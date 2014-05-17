package com.quane.little.ide.presenter

import com.quane.little.ide.view.{ExpressionViewPresenter, PrinterViewPresenter, PrinterView}
import scala._
import com.quane.little.data.model.RecordId
import com.quane.little.data.service.{ExpressionService, FunctionService}
import com.escalatesoft.subcut.inject.{Injectable, BindingModule}
import com.quane.little.language.{FunctionReference, Getter, Expression, Printer}
import com.quane.little.language.data.Value
import com.google.common.base.Objects

/** Presenter for views representing a [[com.quane.little.language.Printer]].
  *
  * @author Sean Connolly
  */
class PrinterPresenter[V <: PrinterView](view: V)(implicit val bindingModule: BindingModule)
  extends PrinterViewPresenter
  with Injectable {

  private val presenterFactory = inject[PresenterFactory]
  private val expressionService = inject[ExpressionService]
  private val functionService = inject[FunctionService]

  private var _value: Option[_ <: ExpressionViewPresenter] = None

  view.registerViewPresenter(this)

  /** Initialize the print presenter.
    *
    * @param p the print expression
    * @return the initialized presenter
    */
  private[presenter] def initialize(p: Printer): PrinterPresenter[V] = {
    value = p.value
    this
  }

  /** Get the print value expression.
    *
    * @return the value expression
    */
  private[presenter] def value: ExpressionViewPresenter =
    _value match {
      case Some(e) => e
      case None => throw new IllegalAccessException("No print expression specified.")
    }

  /** Set the print value expression.
    *
    * @param e the value expression
    */
  private[presenter] def value_=(e: Expression): Unit = {
    val presenter =
      e match {
        case g: Getter =>
          presenterFactory.createGetPresenter(view.createGetStatement()).initialize(g)
        case v: Value =>
          presenterFactory.createValuePresenter(view.createValueStatement()).initialize(v)
        case f: FunctionReference =>
          presenterFactory.createFunctionReference(view.createFunctionReference()).initialize(f)
        case _ => throw new IllegalArgumentException("Not supported: " + e)
      }
    _value = Some(presenter)
  }

  override def requestAddExpression(id: RecordId, index: Int) =
    value = expressionService.findExpression(id)

  // TODO skip if already this function reference
  override def requestAddFunctionReference(id: RecordId, index: Int) =
    value = functionService.findReference(id)

  /** Compile to a [[com.quane.little.language.Printer]].
    *
    * @return the compiled print statement
    */
  override def compile(): Printer = new Printer(value.compile())

  override def toString =
    Objects.toStringHelper(getClass)
      .add("value", _value)
      .toString

}