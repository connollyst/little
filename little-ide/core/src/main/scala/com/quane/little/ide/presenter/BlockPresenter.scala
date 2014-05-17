package com.quane.little.ide.presenter

import com.quane.little.ide.view._
import scala._
import scala.collection.mutable.ListBuffer
import com.quane.little.data.model.RecordId
import com.quane.little.data.service.{FunctionService, ExpressionService, StatementService}
import com.escalatesoft.subcut.inject.{Injectable, BindingModule}
import com.quane.little.language._

/** A presenter for views representing a [[com.quane.little.language.Block]].
  *
  * @author Sean Connolly
  */
class BlockPresenter[V <: BlockView](view: V)(implicit val bindingModule: BindingModule)
  extends BlockViewPresenter
  with Injectable {

  private val presenterFactory = inject[PresenterFactory]
  private val expressionService = inject[ExpressionService]
  private val statementService = inject[StatementService]
  private val functionService = inject[FunctionService]

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
        case s: Setter =>
          val stepView = view.addSetStatement(index)
          presenterFactory.createSetPresenter(stepView).initialize(s)
        case g: Getter =>
          val stepView = view.addGetStatement(index)
          presenterFactory.createGetPresenter(stepView).initialize(g)
        case p: PrintStatement =>
          val stepView = view.addPrintStatement(index)
          new PrintStatementPresenter(stepView).initialize(p)
        case c: Conditional =>
          val stepView = view.addConditional(index)
          new ConditionalPresenter(stepView).initialize(c)
        case f: FunctionReference =>
          val stepView = view.addFunctionReference(index)
          presenterFactory.createFunctionReference(stepView).initialize(f)
        case _ => throw new IllegalArgumentException("Not supported: " + step)
      }
    add(presenter, index)
  }

  /** Add a step at the specified index.
    *
    * @param step the step presenter
    * @param index the step index
    */
  private[presenter] def add(step: EvaluableCodeViewPresenter, index: Int = length): Unit =
    _steps.insert(index, step)

  /** Return the step at the specified index.
    *
    * @param index the step index
    * @return the step presenter
    */
  private[presenter] def get(index: Int): EvaluableCodeViewPresenter =
    _steps(index)

  override def requestAddExpression(id: RecordId, index: Int) =
    add(expressionService.findExpression(id), index)

  override def requestAddStatement(id: RecordId, index: Int) =
    add(statementService.findStatement(id), index)

  override def requestAddFunctionReference(id: RecordId, index: Int) =
    add(functionService.findReference(id), index)

  override def compile(): Block = {
    val block = new Block
    steps.foreach {
      case step: ExpressionViewPresenter => block += step.compile
      case step: StatementViewPresenter => block += step.compile
    }
    block
  }

}
