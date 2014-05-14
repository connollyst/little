package com.quane.little.ide.presenter

import com.quane.little.ide.view.{ExpressionViewPresenter, PrintStatementViewPresenter, PrintStatementView}
import scala._
import com.quane.little.data.model.RecordId
import com.quane.little.data.service.{ExpressionService, FunctionService}
import com.escalatesoft.subcut.inject.{Injectable, BindingModule}
import com.quane.little.language.{FunctionReference, GetStatement, Expression, PrintStatement}
import com.quane.little.language.data.Value

/** Presenter for views representing a [[com.quane.little.language.PrintStatement]].
  *
  * @author Sean Connolly
  */
class PrintStatementPresenter[V <: PrintStatementView](view: V)(implicit val bindingModule: BindingModule)
  extends PrintStatementViewPresenter
  with Injectable {

  private val presenterFactory = inject[PresenterFactory]

  private var _value: Option[_ <: ExpressionViewPresenter] = None

  view.registerViewPresenter(this)

  /** Initialize the print presenter.
    *
    * @param p the print expression
    * @return the initialized presenter
    */
  private[presenter] def initialize(p: PrintStatement): PrintStatementPresenter[V] = {
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
        case g: GetStatement =>
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
    value = ExpressionService().findExpression(id)

  // TODO skip if already this function reference
  override def requestAddFunctionReference(id: RecordId, index: Int) =
    value = FunctionService().findReference(id)

  /** Compile to a [[com.quane.little.language.PrintStatement]].
    *
    * @return the compiled print statement
    */
  override def compile(): PrintStatement = new PrintStatement(value.compile())

}