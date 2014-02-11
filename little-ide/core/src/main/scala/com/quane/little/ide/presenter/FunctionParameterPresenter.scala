package com.quane.little.ide.presenter

import com.quane.little.language.FunctionParameter


class FunctionParameterPresenter(var name: String) {

  def compile(): FunctionParameter = {
    new FunctionParameter(name)
  }

}