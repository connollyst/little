package com.quane.little.ide.presenter

import com.quane.little.ide.view.{BlockViewPresenter, BlockView}
import com.quane.little.language._
import scala._
import scala.collection.mutable.ListBuffer
import com.quane.little.data.model.RecordId
import com.quane.little.data.service.{PrimitiveService, FunctionService}

/** A presenter for views representing a [[com.quane.little.language.Block]].
  *
  * @author Sean Connolly
  */
class BlockPresenter[V <: BlockView](view: V)
  extends ExpressionPresenter
  with BlockViewPresenter {

  private val _steps: ListBuffer[ExpressionPresenter] = new ListBuffer[ExpressionPresenter]

  view.registerViewPresenter(this)

  private[presenter] def length = _steps.length

  private[presenter] def steps: List[ExpressionPresenter] = _steps.toList

  private[presenter] def steps_=(steps: List[ExpressionPresenter]) = {
    _steps.clear()
    _steps ++= steps
  }

  private[presenter] def steps_=[E <: Expression](steps: List[E]) = {
    _steps.clear()
    steps.foreach {
      step => add(step)
    }
  }

  private[presenter] def add(step: Expression): Unit = add(step, length)

  private[presenter] def add(step: Expression, index: Int): Unit = {
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
  private[presenter] def add(step: ExpressionPresenter, index: Int = length): Unit = _steps.insert(index, step)

  /** Return the step at the specified index.
    *
    * @param index the step index
    * @return the step presenter
    */
  private[presenter] def get(index: Int): ExpressionPresenter = _steps(index)


  override def requestAddPrimitive(id: RecordId, index: Int) =
    PrimitiveService().findPrimitive(id) match {
      case get: GetStatement =>
        val presenter = view.addGetStatement(index).initialize(get)
        add(presenter, index)
      case set: SetStatement =>
        val presenter = view.addSetStatement(index).initialize(set)
        add(presenter, index)
      case print: PrintStatement =>
        val presenter = view.addPrintStatement(index).initialize(print)
        add(presenter, index)
      case primitive: Any =>
        throw new IllegalArgumentException("Unsupported primitive " + primitive)
    }

  override def requestAddConditional(index: Int) = add(view.addConditional(index), index)

  override def requestAddFunctionReference(id: RecordId, index: Int) = {
    val fun = FunctionService().findReference(id)
    val presenter = view.addFunctionReference(index).initialize(fun)
    add(presenter, index)
  }

  override def compile: Block = {
    val block = new Block
    steps.foreach {
      step =>
        block += step.compile
    }
    block
  }

}
