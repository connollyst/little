package com.quane.little.ide.presenter

import scala.collection.mutable.ListBuffer
import com.quane.little.language.{FunctionReference, Scope}

class FunctionReferencePresenter(var name: String = "name",
                                 val args: ListBuffer[FunctionArgumentPresenter] = new ListBuffer[FunctionArgumentPresenter])
  extends ExpressionPresenter {

  def addArg(arg: FunctionArgumentPresenter): FunctionReferencePresenter = {
    args += arg
    this
  }

  override def compile(scope: Scope): FunctionReference = {
    val fun = new FunctionReference(scope, name)
    compileArgs(fun)
    fun
  }

  private def compileArgs(fun: FunctionReference) = {
    args.foreach {
      arg: FunctionArgumentPresenter =>
        fun.addArg(arg.name, arg.compile(fun))
    }
  }

}