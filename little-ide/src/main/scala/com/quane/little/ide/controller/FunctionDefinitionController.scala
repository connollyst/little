package com.quane.little.ide.controller

import com.quane.little.language.FunctionDefinition
import scala.collection.mutable.ListBuffer

class FunctionDefinitionController(steps: ListBuffer[ExpressionController] = new ListBuffer[ExpressionController]) {

  var name: String = "name"

  def addStep(step: ExpressionController): FunctionDefinitionController = {
    steps += step
    this
  }

  def compile(): FunctionDefinition = {
    val fun = new FunctionDefinition(name)
    steps.foreach {
      step: ExpressionController =>
        fun.addStep(step.compile(fun))
    }
    fun
  }

}