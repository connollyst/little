package com.quane.little.ide.presenter

import com.quane.little.language.FunctionParameter
import com.quane.little.ide.view.{FunctionParameterViewListener, FunctionParameterView}


class FunctionParameterPresenter[V <: FunctionParameterView](view: V)
  extends FunctionParameterViewListener {

  var name = ""

  view.addViewListener(this)

  override def setParameterName(name: String) = this.name = name

  def compile(): FunctionParameter = {
    new FunctionParameter(name)
  }


}