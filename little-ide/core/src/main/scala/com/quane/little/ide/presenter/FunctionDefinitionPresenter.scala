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

  private[presenter] def add(parameter: FunctionParameterPresenter[_]): FunctionDefinitionPresenter[V] = {
    _params += parameter
    this
  }

  private[presenter] def add(step: ExpressionPresenter): FunctionDefinitionPresenter[V] = {
    _block.add(step)
    this
  }

  private[presenter] def setStepPresenters[E <: ExpressionPresenter](steps: List[E]): Unit = {
    _block.steps = steps
  }

  private[presenter] def setSteps[E <: Expression](steps: List[E]): Unit = {
    _block.setSteps(steps)
  }

  private[presenter] def setParameters(params: List[FunctionParameter]) = {
    _params.clear()
    params.foreach {
      param => addParameter(param)
    }
  }

  private[presenter] def addParameter(param: FunctionParameter): Unit = {
    val presenter = view.createFunctionParameter()
    presenter.name = param.name
    _params += presenter
  }

  private[presenter] def parameters: List[FunctionParameterPresenter[_]] = _params.toList

  private[presenter] def parameters_=(params: List[FunctionParameterPresenter[_]]) = {
    _params.clear()
    _params ++= params
  }


  private[presenter] def name: String = _name

  private[presenter] def name_=(n: String): Unit = {
    _name = n
    view.setName(_name)
  }

  override def onNameChange(name: String): Unit = _name = name

  override def onParamAdded(param: FunctionParameterPresenter[_]): Unit = _params += param

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