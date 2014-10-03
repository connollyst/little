package com.quane.little.ide.presenter

import com.quane.little.ide.view._
import scala._
import scala.collection.mutable.ListBuffer
import com.quane.little.data.model.RecordId
import com.quane.little.data.service.{FunctionService, ExpressionService, StatementService}
import com.escalatesoft.subcut.inject.{Injectable, BindingModule}
import com.quane.little.language._
import com.quane.little.language.math.BasicMath

/** A presenter for views representing a [[com.quane.little.language.Block]].
  *
  * @author Sean Connolly
  */
class BlockPresenter(val view: BlockView)(implicit val bindingModule: BindingModule)
  extends BlockViewPresenter
  with Injectable {

  private val presenterFactory = inject[PresenterFactory]
  private val expressionService = inject[ExpressionService]
  private val statementService = inject[StatementService]
  private val functionService = inject[FunctionService]

  private val _steps = new ListBuffer[EvaluableCodeViewPresenter]

  view.registerViewPresenter(this)
  addCodeMenu(-1)

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
        case m: BasicMath =>
          val stepView = view.addMathStep(index)
          presenterFactory.createMathPresenter(stepView).initialize(m)
        case l: Logic =>
          val stepView = view.addLogicStep(index)
          presenterFactory.createLogicPresenter(stepView).initialize(l)
        case g: Getter =>
          val stepView = view.addGetStep(index)
          presenterFactory.createGetPresenter(stepView).initialize(g)
        case s: Setter =>
          val stepView = view.addSetStep(index)
          presenterFactory.createSetPresenter(stepView).initialize(s)
        case p: Printer =>
          val stepView = view.addPrintStep(index)
          presenterFactory.createPrintPresenter(stepView).initialize(p)
        case c: Conditional =>
          val stepView = view.addConditionalStep(index)
          presenterFactory.createConditionalPresenter(stepView).initialize(c)
        case f: FunctionReference =>
          val stepView = view.addFunctionStep(index)
          presenterFactory.createFunctionReference(stepView).initialize(f)
        case _ => throw new IllegalArgumentException("Not supported: " + step)
      }
    add(presenter, index)
    addCodeMenu(index)
  }

  /** Add a new code menu to the block.
    *
    * @param index the index at which to add the menu to the view
    */
  private def addCodeMenu(index: Int): Unit = {
    presenterFactory.createCodeMenu(view.addCodeMenu(index), this)
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
