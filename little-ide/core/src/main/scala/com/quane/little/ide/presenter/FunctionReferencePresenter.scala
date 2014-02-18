package com.quane.little.ide.presenter

import scala.collection.mutable.ListBuffer
import com.quane.little.language.{FunctionReference, Scope}
import com.quane.little.ide.view.{FunctionReferenceViewListener, FunctionReferenceView}

object FunctionReferencePresenter {

  def apply[V <: FunctionReferenceView](name: String, view: V): FunctionReferencePresenter[_] = {
    println("Creating FunctionReferencePresenter for '" + name + "'")
    name match {
      case "point toward" => pointToward(view)
      case _ => throw new IllegalArgumentException("No FunctionReference '" + name + "'")
    }
  }

  private def pointToward[V <: FunctionReferenceView](view: V): FunctionReferencePresenter[_] = {
    val fun = new FunctionReferencePresenter(view)
    fun.name = "point toward"
    val param1 = view.createArgument()
    val param2 = view.createArgument()
    param1.name = "x"
    param2.name = "y"
    fun.add(param1)
    fun.add(param2)
    fun
  }

}

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

  private[presenter] def name_=(n: String) = {
    _name = n
    view.setName(_name)
  }

  private[presenter] def name: String = _name

  private[presenter] def add(arg: FunctionArgumentPresenter[_]): FunctionReferencePresenter[_] = {
    args += arg
    this
  }

}