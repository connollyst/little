package com.quane.little.ide.presenter

import com.quane.little.language._
import com.quane.little.ide.view.{BlockViewListener, BlockView}
import scala.collection.mutable.ListBuffer

class BlockPresenter[V <: BlockView](view: V,
                                     _steps: ListBuffer[ExpressionPresenter] = new ListBuffer[ExpressionPresenter])
  extends ExpressionPresenter with BlockViewListener {

  def add(step: ExpressionPresenter): Unit = {
    _steps += step
  }

  def steps: List[ExpressionPresenter] = _steps.toList

  def setStepPresenters[E <: ExpressionPresenter](steps: List[E]) = {
    _steps.clear()
    _steps ++= steps
  }

  def setSteps[E <: Expression](steps: List[E]) = {
    _steps.clear()
    steps.foreach {
      step =>
        val presenter =
          step match {
            case s: Set => view.createSetExpression()
            case g: Get => view.createGetExpression()
          }
        add(presenter)
    }
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
