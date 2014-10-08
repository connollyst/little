package com.quane.little.ide.presenter

import com.escalatesoft.subcut.inject.{BindingModule, Injectable}
import com.quane.little.data.service.FunctionService
import com.quane.little.ide.view.{FunctionReferenceView, FunctionReferenceViewPresenter}
import com.quane.little.language.data.Value
import com.quane.little.language.{EvaluableCode, FunctionParameter, FunctionReference}

import scala.collection.mutable.ListBuffer

/** Presenter for views representing a [[com.quane.little.language.FunctionReference]].
  *
  * @author Sean Connolly
  */
class FunctionReferencePresenter(val view: FunctionReferenceView,
                                 val args: ListBuffer[FunctionArgumentPresenter[_]] = new ListBuffer[FunctionArgumentPresenter[_]])(implicit val bindingModule: BindingModule)
  extends FunctionReferenceViewPresenter
  with Injectable {

  private val presenterFactory = inject[PresenterFactory]
  private val functionService = inject[FunctionService]
  private var _name: Option[String] = None

  view.registerViewPresenter(this)

  private[presenter] def initialize(ref: FunctionReference): FunctionReferencePresenter = {
    view.clearArguments()
    val fun = functionService.findDefinition("connollyst", ref.name)
    name = fun.name
    fun.params foreach {
      param => add(param, ref.args.getOrElse(param.name, Value(""))) // TODO param.defaultValue
    }
    this
  }

  private[presenter] def name: String = _name match {
    case Some(n) => n
    case None => throw new IllegalAccessException("Function reference name not set.")
  }

  private[presenter] def name_=(n: String) = {
    _name = Some(n)
    view.setName(n)
  }

  private[presenter] def add(param: FunctionParameter, value: EvaluableCode): Unit = {
    val argView = view.createArgument()
    val argPresenter = presenterFactory.createFunctionArgument(argView)
    argPresenter.initialize(param, value)
    add(argPresenter)
  }

  private[presenter] def add(arg: FunctionArgumentPresenter[_]): Unit =
    args += arg

  override def compile(): FunctionReference = {
    val fun = new FunctionReference(name)
    args.foreach {
      arg => fun.addArg(arg.name, arg.compile())
    }
    fun
  }

}