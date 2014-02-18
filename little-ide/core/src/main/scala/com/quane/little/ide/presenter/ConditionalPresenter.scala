package com.quane.little.ide.presenter

import com.quane.little.ide.view.{ConditionalView, ConditionalViewListener}
import com.quane.little.language.{Conditional, Expression, Scope}

class ConditionalPresenter[V <: ConditionalView](view: V)
  extends ExpressionPresenter with ConditionalViewListener {

  private val _test: ExpressionPresenter = view.setConditionStatement()
  private val _thenBlock: BlockPresenter[_] = view.setThenBlock()
  private val _elseBlock: BlockPresenter[_] = view.setElseBlock()

  view.addViewListener(this)

  private[presenter] def test_=(test: Expression): Unit = {
    // TODO what happens here?
    _test
  }

  def setSteps[E <: Expression](steps: List[E]): Unit = {
    println("Setting conditional steps: " + steps)
    _thenBlock.setSteps(steps)
  }

  override def compile(scope: Scope): Expression = {
    // TODO the else block is not supported yet!!!
    new Conditional(_test.compile(scope), _thenBlock.compile(scope))
  }

}