package com.quane.little.ide.presenter

import com.quane.little.data.model.{FunctionCategory, RecordId, FunctionRecord}
import com.quane.little.ide.view.{EvaluableCodeViewPresenter, FunctionDefinitionView, FunctionDefinitionViewPresenter}
import com.quane.little.language.{FunctionParameter, FunctionDefinition, EvaluableCode}
import scala._
import scala.collection.mutable.ListBuffer
import com.quane.little.data.service.FunctionService
import com.escalatesoft.subcut.inject.{Injectable, BindingModule}

/** Presenter for views representing a [[com.quane.little.language.FunctionDefinition]].
  *
  * @author Sean Connolly
  */
class FunctionDefinitionPresenter[V <: FunctionDefinitionView](view: V)(implicit val bindingModule: BindingModule)
  extends FunctionDefinitionViewPresenter
  with Injectable {

  private val presenterFactory = inject[PresenterFactory]
  private val functionService = inject[FunctionService]

  private var _id: Option[RecordId] = None
  private val _username = "connollyst"
  private var _name = ""
  private val _params = new ListBuffer[FunctionParameterPresenter[_]]
  private val _block = new BlockPresenter(view.createBlock())

  view.registerViewPresenter(this)

  private[presenter] def initialize(id: RecordId, fun: FunctionDefinition): FunctionDefinitionPresenter[V] = {
    _id = Some(id)
    name = fun.name
    steps = fun.steps
    parameters = fun.params
    this
  }

  private[presenter] def name: String = _name

  private[presenter] def name_=(n: String): Unit = {
    _name = n
    view.setName(_name)
  }

  private[presenter] def +=(param: FunctionParameter): Unit = {
    val paramView = view.createFunctionParameter()
    val paramPresenter = presenterFactory.createFunctionParameter(paramView)
    paramPresenter.initialize(param)
    this += paramPresenter
  }

  private[presenter] def +=(param: FunctionParameterPresenter[_]): Unit = _params += param

  private[presenter] def parameters: List[FunctionParameterPresenter[_]] = _params.toList

  private[presenter] def parameters_=(params: List[FunctionParameter]) = {
    _params.clear()
    params.foreach {
      param => this += param
    }
  }

  private[presenter] def +=(step: EvaluableCodeViewPresenter): Unit = _block.add(step)

  private[presenter] def steps: List[_ <: EvaluableCodeViewPresenter] = _block.steps

  private[presenter] def steps_=(steps: List[_ <: EvaluableCode]): Unit = _block.steps = steps

  override def onNameChange(name: String): Unit = _name = name

  override def requestAddParameter() = this += new FunctionParameter("")

  override def compile(): FunctionDefinition = {
    val fun = new FunctionDefinition(_name)
    _params.foreach {
      param =>
        fun.addParam(param.compile())
    }
    // TODO this seems sloppy..
    fun.steps = _block.compile().steps
    fun
  }

  override def save(): FunctionRecord = {
    val fun = compile()
    _id match {
      case Some(id) =>
        println("Saving changes to function definition " + id + "..")
        functionService.update(id, fun)
      case None =>
        println("Saving new function definition..")
        val record = functionService.insert(_username, FunctionCategory.Misc, fun)
        _id = Some(record.id)
        record
    }
  }

}