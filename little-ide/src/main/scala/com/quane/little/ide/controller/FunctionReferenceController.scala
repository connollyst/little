package com.quane.little.ide.controller

import scala.collection.mutable.ListBuffer

import com.quane.little.language.FunctionReference
import com.quane.little.language.Scope

class FunctionReferenceController
  extends ExpressionController {

  var name: String = "name"
  var params = new ListBuffer[FunctionParameterController]

  override def compile(scope: Scope): FunctionReference = {
    new FunctionReference(scope, name)
    // TODO add arguments
  }

}