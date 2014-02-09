package com.quane.little.ide.controller

import com.quane.little.language.FunctionDefinition
import com.quane.little.language.Scope

class FunctionDefinitionController {

  var name: String = "name"

  def addStep(step: ExpressionController) = {
    println("Adding step to definition: " + step)
  }

  def compile(scope: Scope): FunctionDefinition = {
    println("Compiling FunctionDefinition '" + name + "'")
    new FunctionDefinition(name)
  }

}