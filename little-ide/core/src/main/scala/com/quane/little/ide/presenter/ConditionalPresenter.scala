package com.quane.little.ide.presenter

import com.quane.little.ide.view.{ConditionalView, ConditionalViewListener}
import com.quane.little.language.{Conditional, Expression, Scope}

class ConditionalPresenter[V <: ConditionalView](view: V)
  extends ExpressionPresenter with ConditionalViewListener {

  private val _test: ExpressionPresenter = view.createTest()
  private val _block: BlockPresenter[_] = view.createBlock()

  view.addViewListener(this)

  private[presenter] def test_=(test: Expression): Unit = {
    // TODO what happens here?
    _test
  }

  def setSteps[E <: Expression](steps: List[E]): Unit = {
    _block.setSteps(steps)
  }

  override def compile(scope: Scope): Expression = {
    new Conditional(_test.compile(scope), _block.compile(scope))
  }

}