package com.quane.little.ide.presenter

import com.escalatesoft.subcut.inject.{BindingModule, Injectable}
import com.quane.little.data.model.RecordId
import com.quane.little.data.service.CodeService
import com.quane.little.ide.view._
import com.quane.little.language._
import com.quane.little.language.data.ValueType
import com.quane.little.language.data.ValueType.ValueType
import com.quane.little.language.math.BasicMath

import scala.collection.mutable.ListBuffer

/** A presenter for views representing a [[com.quane.little.language.Block]].
  *
  * @author Sean Connolly
  */
class BlockPresenter(val view: BlockView)(implicit val bindingModule: BindingModule)
  extends BlockViewPresenter with Injectable {

  private val presenterFactory = inject[PresenterFactory]
  private val codeService = inject[CodeService]

  private val _steps = new ListBuffer[CodeViewPresenter]

  view.registerViewPresenter(this)
  addCodeMenu(-1)

  private[presenter] def length = _steps.length

  private[presenter] def steps: List[CodeViewPresenter] = _steps.toList

  private[presenter] def steps_=(steps: List[CodeViewPresenter]) = {
    _steps.clear()
    _steps ++= steps
  }

  private[presenter] def steps_=[E <: Code](steps: List[E]) = {
    _steps.clear()
    steps.foreach {
      step => add(step)
    }
  }

  private[presenter] def add(step: Code): Unit = add(step, length)

  private[presenter] def add(step: Code, index: Int): Unit = {
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
  private[presenter] def add(step: CodeViewPresenter, index: Int = length): Unit =
    _steps.insert(index, step)

  /** Return the step at the specified index.
    *
    * @param index the step index
    * @return the step presenter
    */
  private[presenter] def get(index: Int): CodeViewPresenter =
    _steps(index)

  override def acceptedValueType: ValueType = ValueType.Anything

  override def requestAddCode(id: RecordId, index: Int) =
    codeService.find(id) match {
      case Some(code) => add(code, index)
      case _ => throw new IllegalArgumentException("No code " + id)
    }

  override def compile(): Block = {
    val block = new Block
    steps.foreach {
      block += _.compile
    }
    block
  }

}
