package com.quane.little.ide.presenter

import com.quane.little.ide.view.{FunctionArgumentViewPresenter, FunctionArgumentView}
import com.quane.little.language.data.Value
import com.quane.little.language.{FunctionReference, GetStatement, Expression}

/** A presenter for views representing a function reference argument.
  *
  * @author Sean Connolly
  */
class FunctionArgumentPresenter[V <: FunctionArgumentView](view: V)
  extends ExpressionPresenter with FunctionArgumentViewPresenter {

  private var _name: String = ""
  private var _value: Option[ExpressionPresenter] = None

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
  private[presenter] def value: ExpressionPresenter =
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

  // TODO skip if already a get statement
  override def requestAddGetStatement() = _value = Some(view.createGetStatement())

  // TODO skip if already this function reference
  // TODO we need to look up the function definition
  override def requestAddFunctionReference(name: String) = _value = Some(view.createFunctionReference())

  override def compile: Expression = value.compile

}