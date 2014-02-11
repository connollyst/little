package com.quane.little.ide.presenter

import scala._
import com.quane.little.language.FunctionDefinition
import scala.collection.mutable.ListBuffer
import com.quane.little.ide.view.FunctionDefinitionViewListener


class FunctionDefinitionPresenter(var name: String = "name",
                                  params: ListBuffer[FunctionParameterPresenter] = new ListBuffer[FunctionParameterPresenter],
                                  steps: ListBuffer[ExpressionPresenter] = new ListBuffer[ExpressionPresenter])
  extends FunctionDefinitionViewListener {

  def params_=(params: List[FunctionParameterPresenter]) = {
    this.params.clear()
    this.params ++= params
  }

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
        fun.addParam(param.compile())
    }
  }

  private def compileSteps(fun: FunctionDefinition) = {
    steps.foreach {
      step: ExpressionPresenter =>
        fun.addStep(step.compile(fun))
    }
  }

}