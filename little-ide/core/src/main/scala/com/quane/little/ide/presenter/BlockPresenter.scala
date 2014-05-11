package com.quane.little.ide.presenter

import com.quane.little.ide.view._
import com.quane.little.language._
import scala._
import scala.collection.mutable.ListBuffer
import com.quane.little.data.model.RecordId
import com.quane.little.data.service.{StatementService, FunctionService}

/** A presenter for views representing a [[com.quane.little.language.Block]].
  *
  * @author Sean Connolly
  */
class BlockPresenter[V <: BlockView](view: V) extends BlockViewPresenter {

  private val _steps = new ListBuffer[EvaluableCodeViewPresenter]

  view.registerViewPresenter(this)

  private[presenter] def length = _steps.length

  private[presenter] def steps: List[EvaluableCodeViewPresenter] = _steps.toList

  private[presenter] def steps_=(steps: List[EvaluableCodeViewPresenter]) = {
    _steps.clear()
    _steps ++= steps
  }

  private[presenter] def steps_=[E <: EvaluableCode](steps: List[E]) = {
    _steps.clear()
    steps.foreach {
      step => add(step)
    }
  }

  private[presenter] def add(step: EvaluableCode): Unit = add(step, length)

  private[presenter] def add(step: EvaluableCode, index: Int): Unit = {
    val presenter =
      step match {
        case s: SetStatement =>
          view.addSetStatement().initialize(s)
        case g: GetStatement =>
          view.addGetStatement().initialize(g)
        case p: PrintStatement =>
          view.addPrintStatement().initialize(p)
        case c: Conditional =>
          view.addConditional().initialize(c)
        case f: FunctionReference =>
          view.addFunctionReference().initialize(f)
        case _ => throw new IllegalArgumentException("Expression not supported: " + step)
      }
    add(presenter, index)
  }

  /** Add a step at the specified index.
    *
    * @param step the step presenter
    * @param index the step index
    */
  private[presenter] def add(step: EvaluableCodeViewPresenter, index: Int = length): Unit = _steps.insert(index, step)

  /** Return the step at the specified index.
    *
    * @param index the step index
    * @return the step presenter
    */
  private[presenter] def get(index: Int): EvaluableCodeViewPresenter = _steps(index)


  override def requestAddExpression(id: RecordId, index: Int) = {
    val presenter = StatementService().findStatement(id) match {
      case get: GetStatement =>
        view.addGetStatement(index).initialize(get)
      case conditional: Conditional =>
        view.addConditional(index).initialize(conditional)
      case primitive: Any =>
        throw new IllegalArgumentException("Unsupported primitive: " + primitive)
    }
    add(presenter, index)
  }

  override def requestAddStatement(id: RecordId, index: Int) = {
    val presenter = StatementService().findStatement(id) match {
      case set: SetStatement =>
        view.addSetStatement(index).initialize(set)
      case print: PrintStatement =>
        view.addPrintStatement(index).initialize(print)
      case primitive: Any =>
        throw new IllegalArgumentException("Unsupported primitive: " + primitive)
    }
    add(presenter, index)
  }

  override def requestAddFunctionReference(id: RecordId, index: Int) = {
    val fun = FunctionService().findReference(id)
    val presenter = view.addFunctionReference(index).initialize(fun)
    add(presenter, index)
  }

  override def compile: Block = {
    val block = new Block
    steps.foreach {
      _ match {
        case step: ExpressionViewPresenter => block += step.compile
        case step: StatementViewPresenter => block += step.compile
      }
    }
    block
  }

}
