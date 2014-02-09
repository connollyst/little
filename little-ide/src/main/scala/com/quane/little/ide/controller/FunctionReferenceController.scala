package com.quane.little.ide.controller

import com.quane.little.language.FunctionReference
import com.quane.little.language.Scope

class FunctionReferenceController
  extends ExpressionController {

  var name: String = "name"

  override def compile(scope: Scope): FunctionReference = {
    println("Compiling FunctionReference '" + name + "'")
    new FunctionReference(scope, name)
  }

}