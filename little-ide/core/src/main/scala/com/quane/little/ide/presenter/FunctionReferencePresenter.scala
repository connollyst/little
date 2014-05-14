package com.quane.little.ide.presenter

import com.quane.little.ide.view.{FunctionReferenceViewPresenter, FunctionReferenceView}
import com.quane.little.language.{Expression, FunctionReference}
import scala.collection.mutable.ListBuffer
import com.escalatesoft.subcut.inject.{Injectable, BindingModule}

/** Presenter for views representing a [[com.quane.little.language.FunctionReference]].
  *
  * @author Sean Connolly
  */
class FunctionReferencePresenter[V <: FunctionReferenceView](view: V,
                                                             args: ListBuffer[FunctionArgumentPresenter[_]] = new ListBuffer[FunctionArgumentPresenter[_]])(implicit val bindingModule: BindingModule)
  extends FunctionReferenceViewPresenter
  with Injectable {

  private val presenterFactory = inject[PresenterFactory]

  private var _name: String = ""

  view.registerViewPresenter(this)

  private[presenter] def initialize(fun: FunctionReference): FunctionReferencePresenter[V] = {
    view.clearArguments()
    name = fun.name
    fun.args foreach {
      case (name, value) => add(name, value)
    }
    this
  }

  private[presenter] def name: String = _name

  private[presenter] def name_=(n: String) = {
    _name = n
    view.setName(_name)
  }

  private[presenter] def add(name: String, value: Expression): Unit = {
    val argView = view.createArgument()
    val argPresenter = presenterFactory.createArgument(argView)
    argPresenter.initialize(name, value)
    add(argPresenter)
  }

  private[presenter] def add(arg: FunctionArgumentPresenter[_]): Unit =
    args += arg

  override def compile(): FunctionReference = {
    val fun = new FunctionReference(_name)
    compileArgs(fun)
    fun
  }

  private def compileArgs(fun: FunctionReference) =
    args.foreach {
      arg => fun.addArg(arg.name, arg.compile())
    }

}