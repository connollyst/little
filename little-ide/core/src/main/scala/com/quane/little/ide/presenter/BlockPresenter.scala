package com.quane.little.ide.presenter

import com.quane.little.language._
import com.quane.little.ide.view.{BlockViewListener, BlockView}
import scala.collection.mutable.ListBuffer
import scala._

/** A presenter for views representing a [[com.quane.little.language.Block]].
  *
  * @author Sean Connolly
  */
class BlockPresenter[V <: BlockView](view: V)
  extends ExpressionPresenter
  with BlockViewListener {

  private val _steps: ListBuffer[ExpressionPresenter] = new ListBuffer[ExpressionPresenter]

  view.addViewListener(this)

  private[presenter] def length = _steps.length

  private[presenter] def steps: List[ExpressionPresenter] = _steps.toList

  private[presenter] def steps_=(steps: List[ExpressionPresenter]) = {
    _steps.clear()
    _steps ++= steps
  }

  private[presenter] def steps_=[E <: Expression](steps: List[E]) = {
    _steps.clear()
    steps.foreach {
      step => add(step)
    }
  }

  private[presenter] def add(step: Expression): Unit = add(step, length)

  private[presenter] def add(step: Expression, index: Int): Unit = {
    println("Adding step @ " + index + ": " + step)
    val presenter =
      step match {
        case s: SetStatement =>
          view.addSetStatement().initialize(s)
        case g: GetStatement =>
          view.addGetStatement().initialize(g)
        case p: PrintStatement =>
          view.addPrintStatement().initialize(p)
        case c: Conditional =>
          view.addConditional().initialize(c)
        case f: FunctionReference =>
          view.addFunctionReference().initialize(f)
        case _ => throw new IllegalArgumentException("Expression not supported: " + step)
      }
    add(presenter, index)
  }

  /** Add a step at the specified index.
    *
    * @param step the step presenter
    * @param index the step index
    */
  private[presenter] def add(step: ExpressionPresenter, index: Int = length): Unit = _steps.insert(index, step)

  /** Return the step at the specified index.
    *
    * @param index the step index
    * @return the step presenter
    */
  private[presenter] def get(index: Int): ExpressionPresenter = _steps(index)

  override def requestAddConditional(index: Int) = add(view.addConditional(index), index)

  override def requestAddGetStatement(index: Int) = add(view.addGetStatement(index), index)

  override def requestAddSetStatement(index: Int) = add(view.addSetStatement(index), index)

  override def requestAddPrintStatement(index: Int) = add(view.addPrintStatement(index), index)

  override def requestAddFunctionReference(name: String, index: Int) = {
    // TODO lookup function reference
    val fun = view.addFunctionReference(index)
    fun.name = name
    add(fun, index)
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
