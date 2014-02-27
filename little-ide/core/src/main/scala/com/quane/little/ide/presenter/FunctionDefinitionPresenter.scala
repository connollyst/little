package com.quane.little.ide.presenter

import scala._
import com.quane.little.language.{FunctionParameter, Expression, FunctionDefinition}
import scala.collection.mutable.ListBuffer
import com.quane.little.ide.view.{FunctionDefinitionView, FunctionDefinitionViewListener}

/** Presenter for views representing a [[com.quane.little.language.FunctionDefinition]].
  *
  * @author Sean Connolly
  */
class FunctionDefinitionPresenter[V <: FunctionDefinitionView](view: V)
  extends FunctionDefinitionViewListener {

  private var _name = "???"
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

  def setStepPresenters[E <: ExpressionPresenter](steps: List[E]): Unit = {
    _block.steps = steps
  }

  def setSteps[E <: Expression](steps: List[E]): Unit = {
    _block.setSteps(steps)
  }

  def setParameters(params: List[FunctionParameter]) = {
    _params.clear()
    params.foreach {
      param => {
        val presenter = view.createFunctionParameter()
        presenter.name = param.name
        _params += presenter
      }
    }
  }

  def parameters_=(params: List[FunctionParameterPresenter[_]]) = {
    _params.clear()
    _params ++= params
  }

  def addParameter(param: FunctionParameterPresenter[_]): Unit = {
    _params += param
  }

  private[presenter] def name: String = _name

  private[presenter] def name_=(n: String): Unit = {
    _name = n
    view.setName(_name)
  }

  override def onNameChange(name: String): Unit = _name = name

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

}