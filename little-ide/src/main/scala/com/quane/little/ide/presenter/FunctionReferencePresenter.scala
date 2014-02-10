package com.quane.little.ide.presenter

import scala.collection.mutable.ListBuffer

import com.quane.little.language.FunctionReference
import com.quane.little.language.Scope

class FunctionReferencePresenter
  extends ExpressionPresenter {

  var name: String = "name"
  var args = new ListBuffer[FunctionArgumentPresenter]

  override def compile(scope: Scope): FunctionReference = {
    new FunctionReference(scope, name)
    // TODO add arguments
  }

}