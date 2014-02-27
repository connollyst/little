package com.quane.little.ide.presenter

import com.quane.little.language.FunctionParameter
import com.quane.little.ide.view.{FunctionParameterViewListener, FunctionParameterView}

/** A presenter for views representing a function definition parameter.
  *
  * @author Sean Connolly
  */
class FunctionParameterPresenter[V <: FunctionParameterView](view: V)
  extends FunctionParameterViewListener {

  private var _name = ""

  view.addViewListener(this)

  override def onNameChanged(n: String) = _name = n

  private[presenter] def name: String = _name

  private[presenter] def name_=(n: String): Unit = {
    _name = n
    view.setName(_name)
  }

  def compile(): FunctionParameter = {
    new FunctionParameter(name)
  }

}