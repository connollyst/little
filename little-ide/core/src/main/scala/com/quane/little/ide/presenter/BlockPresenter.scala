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
            case s: SetStatement =>
              val set = view.createSetExpression()
              set.name_=(s.name)
              set.value_=(s.valueString)
              set
            case g: GetStatement =>
              val get = view.createGetExpression()
              get.name_=(g.name)
              get
            case _ => throw new IllegalAccessException("Cannot add " + step)
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
