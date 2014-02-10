package com.quane.little.ide.presenter


class FunctionParameterPresenter(var name: String) {

  def compile(): FunctionParameter = {
    new FunctionParameter(name)
  }

}