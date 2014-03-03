package com.quane.little.ide.presenter

import com.quane.little.ide.view.{ConditionalView, ConditionalViewPresenter}
import com.quane.little.language.{Conditional, Expression, Scope}

/** A presenter for views representing an [[com.quane.little.language.Conditional]].
  *
  * @author Sean Connolly
  */
class ConditionalPresenter[V <: ConditionalView](view: V)
  extends ExpressionPresenter with ConditionalViewPresenter {

  private val _condition: ExpressionPresenter = view.setConditionStatement()
  private val _thenBlock: BlockPresenter[_] = view.setThenBlock()
  private val _elseBlock: BlockPresenter[_] = view.setElseBlock()

  view.registerViewPresenter(this)

  private[presenter] def initialize(c: Conditional): ConditionalPresenter[V] = {
    condition = c.test
    steps = c.steps
    // TODO initialize else steps
    this
  }

  private[presenter] def condition: ExpressionPresenter = _condition

  private[presenter] def condition_=(condition: Expression): Unit =
  // TODO we need to create a view depending on the expression type
    throw new NotImplementedError("condition not supported yet")

  private[presenter] def steps: List[ExpressionPresenter] = _thenBlock.steps

  private[presenter] def steps_=(steps: List[Expression]): Unit = _thenBlock.steps = steps

  override def compile(scope: Scope): Conditional = {
    // TODO the else block is not supported yet!!!
    new Conditional(_condition.compile(scope), _thenBlock.compile(scope))
  }

}