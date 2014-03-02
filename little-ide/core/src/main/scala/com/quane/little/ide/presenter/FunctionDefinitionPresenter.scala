package com.quane.little.ide.presenter

import scala._
import com.quane.little.language.{FunctionParameter, Expression, FunctionDefinition}
import scala.collection.mutable.ListBuffer
import com.quane.little.ide.view.{FunctionDefinitionView, FunctionDefinitionViewPresenter}

/** Presenter for views representing a [[com.quane.little.language.FunctionDefinition]].
  *
  * @author Sean Connolly
  */
class FunctionDefinitionPresenter[V <: FunctionDefinitionView](view: V)
  extends FunctionDefinitionViewPresenter {

  private var _name = "???"
  private val _params = new ListBuffer[FunctionParameterPresenter[_]]
  private val _block = view.createBlock()

  view.addViewListener(this)

  private[presenter] def initialize(fun: FunctionDefinition): FunctionDefinitionPresenter[V] = {
    name = fun.name
    steps = fun.steps
    parameters = fun.params
    this
  }

  private[presenter] def +=(param: FunctionParameter): Unit = {
    val presenter = view.createFunctionParameter()
    presenter.name = param.name
    this += presenter
  }

  private[presenter] def +=(param: FunctionParameterPresenter[_]): Unit = _params += param

  private[presenter] def parameters: List[FunctionParameterPresenter[_]] = _params.toList

  private[presenter] def parameters_=(params: List[FunctionParameter]) = {
    _params.clear()
    params.foreach {
      param => this += param
    }
  }

  private[presenter] def +=(step: ExpressionPresenter): Unit = _block.add(step)

  private[presenter] def steps: List[_ <: ExpressionPresenter] = _block.steps

  private[presenter] def steps_=(steps: List[_ <: Expression]): Unit = _block.steps = steps

  private[presenter] def name: String = _name

  private[presenter] def name_=(n: String): Unit = {
    _name = n
    view.setName(_name)
  }

  override def onNameChange(name: String): Unit = _name = name

  override def onParamAdded(param: FunctionParameterPresenter[_]) = this += param

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