package com.quane.little.ide.presenter

import scala._
import com.quane.little.language.FunctionDefinition
import scala.collection.mutable.ListBuffer
import com.quane.little.ide.view.{FunctionDefinitionView, FunctionDefinitionViewListener}

object FunctionDefinitionPresenter {

  def apply[V <: FunctionDefinitionView](name: String, view: V): FunctionDefinitionPresenter[_] = {
    println("Creating FunctionDefinitionPresenter for '" + name + "'")
    name match {
      case "move forward" => moveForward(name, view)
      case _ => throw new IllegalArgumentException("No FunctionDefinition '" + name + "'")
    }
  }

  private def moveForward[V <: FunctionDefinitionView](name: String, view: V): FunctionDefinitionPresenter[_] = {
    // TODO this isn't backed by a presenter
    val fun = new FunctionDefinitionPresenter(view)
    fun.name = name
    val param1 = view.createFunctionParameter()
    val param2 = view.createFunctionParameter()
    param1.name = "x"
    param2.name = "y"
    fun.add(param1)
    fun.add(param2)
    val step1 = view.createFunctionReference()
    step1.name = "point toward"
    // TODO add arguments
    fun.add(step1)
    //        fun.add(
    //          new FunctionReferenceComponent(
    //            new FunctionReferencePresenter,
    //            "point toward",
    //            new FunctionArgumentComponent("x"),
    //            new FunctionArgumentComponent("y")))
    //    fun.addStep(
    //      new FunctionReferenceComponent(
    //        new FunctionReferencePresenter,
    //        "move",
    //        new FunctionArgumentComponent("speed")))
    //    val ifElse = new ConditionalComponent("touching [location]")
    //    ifElse.addThen(new PrintComponent("done"))
    //    ifElse.addElse(
    //      new FunctionReferenceComponent(
    //        new FunctionReferencePresenter,
    //        "move toward",
    //        new FunctionArgumentComponent("x"),
    //        new FunctionArgumentComponent("y")))
    //    fun.stepList.add(ifElse)
    //    fun.stepList.add(new PrintComponent("done"))
    fun
  }

}

class FunctionDefinitionPresenter[V <: FunctionDefinitionView](view: V,
                                                               params: ListBuffer[FunctionParameterPresenter[_]] = new ListBuffer[FunctionParameterPresenter[_]],
                                                               steps: ListBuffer[ExpressionPresenter] = new ListBuffer[ExpressionPresenter])
  extends FunctionDefinitionViewListener {

  private var _name = ""

  view.addViewListener(this)

  def add(parameter: FunctionParameterPresenter[_]): FunctionDefinitionPresenter[V] = {
    params += parameter
    this
  }

  def add(step: ExpressionPresenter): FunctionDefinitionPresenter[V] = {
    steps += step
    this
  }

  override def compile(): FunctionDefinition = {
    val fun = new FunctionDefinition(_name)
    compileParams(fun)
    compileSteps(fun)
    fun
  }

  private def compileParams(fun: FunctionDefinition) = {
    params.foreach {
      param =>
        fun.addParam(param.compile())
    }
  }

  private def compileSteps(fun: FunctionDefinition) = {
    steps.foreach {
      step: ExpressionPresenter =>
        fun.addStep(step.compile(fun))
    }
  }

  private[presenter] def name_=(n: String): Unit = {
    _name = n
    view.setName(_name)
  }

  private[presenter] def name: String = _name

}