package com.quane.little.ide.presenter

import com.quane.little.ide.view.{ConditionalView, ConditionalViewListener}
import com.quane.little.language.{Conditional, Expression, Scope}

/** A presenter for views representing an [[com.quane.little.language.Conditional]].
  *
  * @author Sean Connolly
  */
class ConditionalPresenter[V <: ConditionalView](view: V)
  extends ExpressionPresenter with ConditionalViewListener {

  private val _condition: ExpressionPresenter = view.setConditionStatement()
  private val _thenBlock: BlockPresenter[_] = view.setThenBlock()
  private val _elseBlock: BlockPresenter[_] = view.setElseBlock()

  view.addViewListener(this)

  private[presenter] def condition: ExpressionPresenter = _condition

  private[presenter] def condition_=(condition: Expression): Unit = {
    // TODO i think we need to create a view depending on the expression type?
    _condition
  }

  private[presenter] def steps: List[ExpressionPresenter] = _thenBlock.steps

  private[presenter] def steps_=(steps: List[Expression]): Unit = {
    println("Setting conditional THEN steps: " + steps)
    _thenBlock.steps = steps
  }

  override def compile(scope: Scope): Conditional = {
    // TODO the else block is not supported yet!!!
    new Conditional(_condition.compile(scope), _thenBlock.compile(scope))
  }

}