package com.quane.little.ide.presenter

import com.quane.little.language._
import com.quane.little.ide.view.{BlockViewListener, BlockView}
import scala.collection.mutable.ListBuffer

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

  private[presenter] def setSteps(steps: List[Expression]) = {
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
          print.expression = p.value
          print
        case c: Conditional =>
          val con = view.addConditional()
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
