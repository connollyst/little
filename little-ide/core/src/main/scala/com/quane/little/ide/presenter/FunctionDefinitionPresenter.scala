package com.quane.little.ide.presenter

import com.escalatesoft.subcut.inject.{BindingModule, Injectable}
import com.quane.little.data.model.CodeCategory.CodeCategory
import com.quane.little.data.model.{CodeCategory, FunctionId, FunctionRecord}
import com.quane.little.data.service.FunctionService
import com.quane.little.ide.view.{CodeViewPresenter, FunctionDefinitionView, FunctionDefinitionViewPresenter}
import com.quane.little.language.data.ValueType
import com.quane.little.language.{Code, FunctionDefinition, FunctionParameter}

import scala.collection.mutable.ListBuffer

/** Presenter for views representing a [[com.quane.little.language.FunctionDefinition]].
  *
  * @author Sean Connolly
  */
class FunctionDefinitionPresenter[V <: FunctionDefinitionView](view: V)(implicit val bindingModule: BindingModule)
  extends FunctionDefinitionViewPresenter
  with Injectable {

  private val presenterFactory = inject[PresenterFactory]
  private val functionService = inject[FunctionService]

  private var _id: Option[FunctionId] = None
  private var _name = ""
  private var _category = CodeCategory.Misc
  private val _params = new ListBuffer[FunctionParameterPresenter[_]]
  private val _block = new BlockPresenter(view.createBlock())
  private val _username = "connollyst"

  view.registerViewPresenter(this)

  private[presenter] def initialize(id: FunctionId, fun: FunctionDefinition): FunctionDefinitionPresenter[V] = {
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

  private[presenter] def +=(step: CodeViewPresenter): Unit = _block.add(step)

  private[presenter] def steps: List[_ <: CodeViewPresenter] = _block.steps

  private[presenter] def steps_=(steps: List[_ <: Code]): Unit = _block.steps = steps

  private[presenter] def category: CodeCategory = _category

  // TODO notify view
  private[presenter] def category_=(category: CodeCategory) = _category = category

  override def onNameChange(name: String): Unit = _name = name

  override def requestAddParameter() = this += new FunctionParameter("", ValueType.String)

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
        functionService.update(id, category, fun)
      case None =>
        println("Saving new function definition..")
        val record = functionService.insert(_username, category, fun)
        _id = Some(record.id)
        record
    }
  }

}