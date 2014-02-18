package com.quane.little.ide.presenter

import scala.collection.mutable.ListBuffer
import com.quane.little.language.{FunctionReference, Scope}
import com.quane.little.ide.view.{FunctionReferenceViewListener, FunctionReferenceView}

class FunctionReferencePresenter[V <: FunctionReferenceView](view: V,
                                                             args: ListBuffer[FunctionArgumentPresenter[_]] = new ListBuffer[FunctionArgumentPresenter[_]])
  extends ExpressionPresenter with FunctionReferenceViewListener {

  private var _name: String = ""

  override def compile(scope: Scope): FunctionReference = {
    val fun = new FunctionReference(scope, _name)
    compileArgs(fun)
    fun
  }

  private def compileArgs(fun: FunctionReference) = {
    args.foreach {
      arg =>
        fun.addArg(arg.name, arg.compile(fun))
    }
  }

  private[presenter] def name: String = _name

  private[presenter] def name_=(n: String) = {
    _name = n
    view.setName(_name)
  }

  private[presenter] def add(arg: FunctionArgumentPresenter[_]): FunctionReferencePresenter[_] = {
    args += arg
    this
  }

}