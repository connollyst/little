package com.quane.little.ide.presenter

import com.quane.little.ide.view.{ValueView, ValueViewPresenter}
import com.quane.little.language.data.Value

/** A presenter for views representing a [[com.quane.little.language.data.Value]].
  *
  * @author Sean Connolly
  */
class ValuePresenter(val view: ValueView)
  extends ValueViewPresenter {

  private var _value: String = ""

  view.registerViewPresenter(this)
  view.setValue(_value)

  /** Initialize the value presenter.
    *
    * @param v the value
    * @return the initialized presenter
    */
  private[presenter] def initialize(v: Value): ValuePresenter = {
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
    * @return the compiled expression
    */
  override def compile() = Value(_value)

}
