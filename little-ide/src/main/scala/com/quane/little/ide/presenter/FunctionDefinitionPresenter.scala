package com.quane.little.ide.presenter

import com.quane.little.language.FunctionDefinition
import scala.collection.mutable.ListBuffer

class FunctionDefinitionPresenter(steps: ListBuffer[ExpressionPresenter] = new ListBuffer[ExpressionPresenter]) {

  var name: String = "name"
  var params = new ListBuffer[FunctionParameterPresenter]

  def addStep(step: ExpressionPresenter): FunctionDefinitionPresenter = {
    steps += step
    this
  }

  def compile(): FunctionDefinition = {
    val fun = new FunctionDefinition(name)
    // TODO add parameters
    steps.foreach {
      step: ExpressionPresenter =>
        fun.addStep(step.compile(fun))
    }
    fun
  }

}