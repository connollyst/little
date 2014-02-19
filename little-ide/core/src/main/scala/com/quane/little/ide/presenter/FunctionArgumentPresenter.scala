package com.quane.little.ide.presenter

import com.quane.little.language.{Expression, Scope}
import com.quane.little.language.data.Value
import com.quane.little.ide.view.{FunctionArgumentViewListener, FunctionArgumentView}

/** A presenter for views representing a function reference argument.
  *
  * @author Sean Connolly
  */
class FunctionArgumentPresenter[V <: FunctionArgumentView](view: V)
  extends FunctionArgumentViewListener {

  private var _name: String = ""
  private var _value: String = ""

  view.addViewListener(this)

  def compile(scope: Scope): Expression = {
    new Value(_value)
  }

  private[presenter] def name: String = _name

  private[presenter] def name_=(n: String) = {
    _name = n
    view.setName(_name)
  }

  private[presenter] def value: String = _value

  private[presenter] def value_=(v: String) = {
    _value = v
    view.setValue(_value)
  }

}