package com.quane.little.ide.presenter


class FunctionArgumentPresenter(var name: String,
                                var value: String = "") {

  def compile(scope: Scope): Expression = {
    new Value(value)
  }

}