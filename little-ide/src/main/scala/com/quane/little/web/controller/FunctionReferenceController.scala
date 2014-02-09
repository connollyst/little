package com.quane.little.web.controller

import com.quane.little.language.FunctionReference
import com.quane.little.language.Scope

class FunctionReferenceController
  extends Controller[FunctionReference] {

  var name: String = "name"

  override def compile(scope: Scope): FunctionReference = {
    new FunctionReference(scope, name)
  }

}