package com.quane.little.ide.presenter

import com.quane.little.language.data.Value
import com.quane.little.language.{Expression, Scope}

class FunctionArgumentPresenter(var name: String,
                                var value: String = "") {

  def compile(scope: Scope): Expression = {
    new Value(value)
  }

}