package com.quane.little.ide.controller

import com.quane.little.language.data.Value
import com.quane.little.language.{Print, Expression, Scope}

class FunctionArgumentController {

  def compile(scope: Scope): Expression = {
    new Print(new Value("TODO"))
  }

}