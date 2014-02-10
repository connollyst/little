package com.quane.little.ide.presenter

import com.quane.little.language.data.Value
import com.quane.little.language.{Print, Expression, Scope}

class FunctionArgumentPresenter {

  def compile(scope: Scope): Expression = {
    new Print(new Value("TODO"))
  }

}