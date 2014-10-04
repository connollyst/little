package com.quane.little.ide.presenter

import com.quane.little.ide.view.{PrinterViewPresenter, ExpressionViewPresenter, FunctionArgumentViewPresenter, FunctionArgumentView}
import com.quane.little.language.data.Value
import com.quane.little.language.{Logic, FunctionReference, Expression, Getter}
import com.quane.little.data.model.RecordId
import com.quane.little.data.service.{ExpressionService, FunctionService}
import com.escalatesoft.subcut.inject.{Injectable, BindingModule}
import com.quane.little.language.math.BasicMath

/** A presenter for views representing a function reference argument.
  *
  * @author Sean Connolly
  */
class FunctionArgumentPresenter[V <: FunctionArgumentView](view: V)(implicit val bindingModule: BindingModule)
  extends FunctionArgumentViewPresenter
  with Injectable {

  private val presenterFactory = inject[PresenterFactory]
  private val expressionService = inject[ExpressionService]
  private val functionService = inject[FunctionService]

  private var _name: String = ""
  private var _value: Option[ExpressionViewPresenter] = None

  view.registerViewPresenter(this)
  view.setName(_name)
  createCodeMenu()

  private[presenter] def initialize(name: String, value: Expression): FunctionArgumentPresenter[V] = {
    this.name = name
    this.value = value
    this
  }

  private[presenter] def name: String = _name

  private[presenter] def name_=(n: String) = {
    _name = n
    view.setName(_name)
  }

  /** Get the argument value expression.
    *
    * @return the value expression
    */
  private[presenter] def value: ExpressionViewPresenter =
    _value match {
      case Some(e) => e
      case None => throw new IllegalAccessException("No argument value expression specified.")
    }

  /** Set the argument value expression.
    *
    * @param e the value expression
    */
  private[presenter] def value_=(e: Expression): Unit = {
    val presenter =
      e match {
        // TODO skip if nothing has changed
        case g: Getter =>
          presenterFactory.createGetPresenter(view.createGetterExpression()).initialize(g)
        case m: BasicMath =>
          presenterFactory.createMathPresenter(view.createMathExpression()).initialize(m)
        case l: Logic =>
          presenterFactory.createLogicPresenter(view.createLogicExpression()).initialize(l)
        case v: Value =>
          presenterFactory.createValuePresenter(view.createValueExpression()).initialize(v)
        case f: FunctionReference =>
          presenterFactory.createFunctionReference(view.createFunctionReference()).initialize(f)
        case _ => throw new IllegalArgumentException("Expression not supported: " + e)
      }
    _value = Some(presenter)
  }

  /** Create an add a new code menu to the printer.
    */
  private def createCodeMenu(): Unit =
    presenterFactory.createCodeMenu[FunctionArgumentViewPresenter](view.createCodeMenu(), this)

  override def requestAddExpression(id: RecordId, index: Int) =
    value = expressionService.findExpression(id)

  override def requestAddFunctionReference(id: RecordId, index: Int) =
    value = functionService.findReference(id)

  override def compile(): Expression = value.compile()

}