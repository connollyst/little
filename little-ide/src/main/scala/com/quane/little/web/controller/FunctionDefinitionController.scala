package com.quane.little.web.controller

import com.quane.little.language.Expression
import com.quane.little.language.FunctionDefinition
import com.quane.little.language.Scope

class FunctionDefinitionController {

  var name: String = "name"

  def compile(scope: Scope): FunctionDefinition = {
    new FunctionDefinition(name)
  }

}