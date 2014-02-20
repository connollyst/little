package com.quane.little.ide.presenter

import com.quane.little.language._
import com.quane.little.ide.view.{BlockViewListener, BlockView}
import scala.collection.mutable.ListBuffer

/** A presenter for views representing an [[com.quane.little.language.Block]].
  *
  * @author Sean Connolly
  */
class BlockPresenter[V <: BlockView](view: V)
  extends ExpressionPresenter
  with BlockViewListener {

  private val _steps: ListBuffer[ExpressionPresenter] = new ListBuffer[ExpressionPresenter]

  view.addViewListener(this)

  private[presenter] def add(step: ExpressionPresenter): Unit = _steps += step

  private[presenter] def steps: List[ExpressionPresenter] = _steps.toList

  private[presenter] def steps_=[E <: ExpressionPresenter](steps: List[E]) = {
    _steps.clear()
    _steps ++= steps
  }

  private[presenter] def setSteps[E <: Expression](steps: List[E]) = {
    _steps.clear()
    steps.foreach {
      step => add(step)
    }
  }

  private[presenter] def add[E <: Expression](step: E): Unit = {
    println("Adding step: " + step)
    val presenter =
      step match {
        case s: SetStatement =>
          val set = view.addSetStatement()
          set.name = s.name
          set.value = s.valueString
          set
        case g: GetStatement =>
          val get = view.addGetStatement()
          get.name = g.name
          get
        case p: PrintStatement =>
          val print = view.addPrintStatement()
          print.value = p.valueString
          print
        case c: Conditional =>
          val con = view.addConditionalStatement()
          con.condition_=(c.test)
          con.setSteps(c.steps)
          con
        case f: FunctionReference =>
          val fun = view.addFunctionReference()
          fun.name = f.name
          // TODO set function name & args
          fun
        case _ => throw new IllegalAccessException("Cannot add " + step)
      }
    add(presenter)
  }

  override def compile(scope: Scope): Block = {
    val block = new Block(scope)
    steps.foreach {
      step =>
        block.addStep(step.compile(block))
    }
    block
  }

}
