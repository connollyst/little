package com.quane.little.ide.presenter

import com.quane.little.language.FunctionDefinition
import scala.collection.mutable.ListBuffer

class FunctionDefinitionPresenter(var name: String = "name",
                                  params: ListBuffer[FunctionParameterPresenter] = new ListBuffer[FunctionParameterPresenter],
                                  steps: ListBuffer[ExpressionPresenter] = new ListBuffer[ExpressionPresenter]) {

  def addStep(step: ExpressionPresenter): FunctionDefinitionPresenter = {
    steps += step
    this
  }

  def addParam(param: FunctionParameterPresenter): FunctionDefinitionPresenter = {
    params += param
    this
  }

  def compile(): FunctionDefinition = {
    val fun = new FunctionDefinition(name)
    compileParams(fun)
    compileSteps(fun)
    fun
  }

  private def compileParams(fun: FunctionDefinition) = {
    params.foreach {
      param: FunctionParameterPresenter =>
        fun.addParam(param.compile(fun))
    }
  }

  private def compileSteps(fun: FunctionDefinition) = {
    steps.foreach {
      step: ExpressionPresenter =>
        fun.addStep(step.compile(fun))
    }
  }

}