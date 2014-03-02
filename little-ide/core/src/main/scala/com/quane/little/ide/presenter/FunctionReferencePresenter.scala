package com.quane.little.ide.presenter

import scala.collection.mutable.ListBuffer
import com.quane.little.language.{Expression, FunctionReference, Scope}
import com.quane.little.ide.view.{FunctionReferenceViewPresenter, FunctionReferenceView}

/** Presenter for views representing a [[com.quane.little.language.FunctionReference]].
  *
  * @author Sean Connolly
  */
class FunctionReferencePresenter[V <: FunctionReferenceView](view: V,
                                                             args: ListBuffer[FunctionArgumentPresenter[_]] = new ListBuffer[FunctionArgumentPresenter[_]])
  extends ExpressionPresenter
  with FunctionReferenceViewPresenter {

  private var _name: String = "???"

  view.addViewPresenter(this)

  private[presenter] def initialize(fun: FunctionReference): FunctionReferencePresenter[V] = {
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

  private[presenter] def add(name: String, value: Expression): Unit =
    add(view.createArgument().initialize(name, value))

  private[presenter] def add(arg: FunctionArgumentPresenter[_]): Unit =
    args += arg

  override def compile(scope: Scope): FunctionReference = {
    val fun = new FunctionReference(scope, _name)
    compileArgs(fun)
    fun
  }

  private def compileArgs(fun: FunctionReference) = args.foreach {
    arg => fun.addArg(arg.name, arg.compile(fun))
  }

}