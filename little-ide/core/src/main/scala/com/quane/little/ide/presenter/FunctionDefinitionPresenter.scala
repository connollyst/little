package com.quane.little.ide.presenter

import com.quane.little.data.model.{RecordId, FunctionRecord}
import com.quane.little.ide.model.FunctionService
import com.quane.little.ide.view.{FunctionDefinitionView, FunctionDefinitionViewPresenter}
import com.quane.little.language.{FunctionDefinition, FunctionParameter, Expression}
import scala._
import scala.collection.mutable.ListBuffer

/** Presenter for views representing a [[com.quane.little.language.FunctionDefinition]].
  *
  * @author Sean Connolly
  */
class FunctionDefinitionPresenter[V <: FunctionDefinitionView](view: V)
  extends FunctionDefinitionViewPresenter {

  private var _id: Option[RecordId] = None
  private val _username = "connollyst"
  private var _name = "???"
  private val _params = new ListBuffer[FunctionParameterPresenter[_]]
  private val _block = view.createBlock()

  view.registerViewPresenter(this)

  private[presenter] def initialize(id: RecordId, fun: FunctionDefinition): FunctionDefinitionPresenter[V] = {
    _id = Some(id)
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

  override def compile: FunctionDefinition = {
    val fun = new FunctionDefinition(_name)
    _params.foreach {
      param =>
        fun.addParam(param.compile)
    }
    fun.steps = _block.compile.steps // TODO this seems sloppy..?
    fun
  }

  override def save: FunctionRecord = {
    val fun = compile
    _id match {
      case Some(id) =>
        println("Saving changes to " + id + "..")
        FunctionService().update(id, fun)
      case None =>
        println("Saving new function definition..")
        val record = FunctionService().insert(_username, fun)
        _id = Some(record.id)
        record
    }
  }

}