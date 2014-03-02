package com.quane.little.ide.presenter

import com.quane.little.ide.view.{ValueView, ValueViewPresenter}
import com.quane.little.language.Scope
import com.quane.little.language.data.Value

/** A presenter for views representing a [[com.quane.little.language.data.Value]].
  *
  * @author Sean Connolly
  */
class ValuePresenter[V <: ValueView](view: V)
  extends ExpressionPresenter
  with ValueViewPresenter {

  private var _value: String = ""

  view.addViewPresenter(this)
  view.setValue(_value)

  /** Initialize the value presenter.
    *
    * @param v the value
    * @return the initialized presenter
    */
  private[presenter] def initialize(v: Value): ValuePresenter[V] = {
    value = v.asText
    this
  }

  private[presenter] def value: String = _value

  private[presenter] def value_=(value: String): Unit = {
    _value = value
    view.setValue(value)
  }

  override def onValueChange(value: String) = _value = value

  /** Compile the presented data to a [[com.quane.little.language.data.Value]].
    *
    * @param scope the scope in which to compile
    * @return the compiled expression
    */
  override def compile(scope: Scope) = new Value(_value)

}
