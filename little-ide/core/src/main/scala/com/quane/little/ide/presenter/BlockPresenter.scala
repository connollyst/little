package com.quane.little.ide.presenter

import com.quane.little.language.{Block, Scope}
import com.quane.little.ide.view.{BlockViewListener, BlockView}
import scala.collection.mutable.ListBuffer

class BlockPresenter[V <: BlockView](view: V,
                                     steps: ListBuffer[ExpressionPresenter] = new ListBuffer[ExpressionPresenter])
  extends ExpressionPresenter with BlockViewListener {

  def add(step: ExpressionPresenter): Unit = {
    steps += step
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
