package com.quane.little.ide.presenter

import com.quane.little.ide.view.{ExpressionViewPresenter, FunctionArgumentViewPresenter, FunctionArgumentView}
import com.quane.little.language.data.Value
import com.quane.little.language.{FunctionReference, GetStatement, Expression}
import com.quane.little.data.model.RecordId
import com.quane.little.data.service.{ExpressionService, FunctionService}

/** A presenter for views representing a function reference argument.
  *
  * @author Sean Connolly
  */
class FunctionArgumentPresenter[V <: FunctionArgumentView](view: V)
  extends FunctionArgumentViewPresenter {

  private var _name: String = ""
  private var _value: Option[ExpressionViewPresenter] = None

  view.registerViewPresenter(this)

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
        case v: Value =>
          view.createValueStatement().initialize(v)
        case g: GetStatement =>
          view.createGetStatement().initialize(g)
        case f: FunctionReference =>
          view.createFunctionReference().initialize(f)
        case _ => throw new IllegalArgumentException("Expression not supported: " + e)
      }
    _value = Some(presenter)
  }

  // TODO skip if already a value statement
  override def requestAddTextLiteral() = _value = Some(view.createValueStatement())

  override def requestAddExpression(id: RecordId, index: Int) =
    value = ExpressionService().findExpression(id)

  override def requestAddFunctionReference(id: RecordId, index: Int) =
    value = FunctionService().findReference(id)

  override def compile: Expression = value.compile

}