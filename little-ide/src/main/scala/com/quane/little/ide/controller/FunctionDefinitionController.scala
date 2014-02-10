package com.quane.little.ide.controller

import com.quane.little.language.FunctionDefinition
import scala.collection.mutable.ListBuffer

class FunctionDefinitionController(steps: ListBuffer[ExpressionController] = new ListBuffer[ExpressionController]) {

  var name: String = "name"
  var params = new ListBuffer[FunctionParameterController]

  def addStep(step: ExpressionController): FunctionDefinitionController = {
    steps += step
    this
  }

  def compile(): FunctionDefinition = {
    val fun = new FunctionDefinition(name)
    // TODO add parameters
    steps.foreach {
      step: ExpressionController =>
        fun.addStep(step.compile(fun))
    }
    fun
  }

}