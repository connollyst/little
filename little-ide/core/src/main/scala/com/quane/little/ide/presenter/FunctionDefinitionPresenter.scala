package com.quane.little.ide.presenter

import scala._
import com.quane.little.language.{Expression, FunctionDefinition}
import scala.collection.mutable.ListBuffer
import com.quane.little.ide.view.{FunctionDefinitionView, FunctionDefinitionViewListener}

class FunctionDefinitionPresenter[V <: FunctionDefinitionView](view: V)
  extends FunctionDefinitionViewListener {

  private var _name = ""
  private val _params = new ListBuffer[FunctionParameterPresenter[_]]
  private val _block = view.createBlock()

  view.addViewListener(this)

  def add(parameter: FunctionParameterPresenter[_]): FunctionDefinitionPresenter[V] = {
    _params += parameter
    this
  }

  def add(step: ExpressionPresenter): FunctionDefinitionPresenter[V] = {
    _block.add(step)
    this
  }

  def setStepPresenters[E <: ExpressionPresenter](steps: List[E]) = {
    _block.setStepPresenters(steps)
  }

  def setSteps[E <: Expression](steps: List[E]) = {
    _block.setSteps(steps)
  }

  override def compile(): FunctionDefinition = {
    val fun = new FunctionDefinition(_name)
    _params.foreach {
      param =>
        fun.addParam(param.compile())
    }
    // TODO this seems sloppy..?
    fun.steps = _block.compile(fun).steps
    fun
  }

  private[presenter] def name_=(n: String): Unit = {
    _name = n
    view.setName(_name)
  }

  private[presenter] def name: String = _name

}