package com.quane.little.ide.presenter

import com.quane.little.language.{Scope, Expression}
import com.quane.little.language.data.Value

class FunctionArgumentPresenter(var name: String,
                                var value: String = "") {

  def compile(scope: Scope): Expression = {
    new Value(value)
  }

}