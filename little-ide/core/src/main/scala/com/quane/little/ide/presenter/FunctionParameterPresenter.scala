package com.quane.little.ide.presenter

import com.quane.little.ide.view.{FunctionParameterViewPresenter, FunctionParameterView}

/** A presenter for views representing a function definition parameter.
  *
  * @author Sean Connolly
  */
class FunctionParameterPresenter[V <: FunctionParameterView](view: V)
  extends FunctionParameterViewPresenter {

  private var _name = ""

  view.registerViewPresenter(this)

  override def onNameChanged(n: String) = _name = n

  private[presenter] def initialize(param: FunctionParameter): FunctionParameterPresenter[V] = {
    name = param.name
    this
  }

  private[presenter] def name: String = _name

  private[presenter] def name_=(n: String): Unit = {
    _name = n
    view.setName(_name)
  }

  def compile(): FunctionParameter = new FunctionParameter(name)

}