package com.quane.little.ide.presenter

import com.quane.little.language.{FunctionReference, GetStatement, Expression, Scope}
import com.quane.little.language.data.Value
import com.quane.little.ide.view.{FunctionArgumentViewPresenter, FunctionArgumentView}

/** A presenter for views representing a function reference argument.
  *
  * @author Sean Connolly
  */
class FunctionArgumentPresenter[V <: FunctionArgumentView](view: V)
  extends FunctionArgumentViewPresenter {

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

  override def onValueChange(p: ExpressionPresenter) = _value = Some(p)

  def compile(scope: Scope): Expression = value.compile(scope)

}