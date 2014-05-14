package com.quane.little.ide.presenter

import com.quane.little.ide.view._
import com.quane.little.language.{EvaluableCode, Conditional, Expression}
import com.google.common.base.Objects
import com.escalatesoft.subcut.inject.{Injectable, BindingModule}
import com.quane.little.language.exceptions.NotImplementedError

/** A presenter for views representing an [[com.quane.little.language.Conditional]].
  *
  * @author Sean Connolly
  */
class ConditionalPresenter[V <: ConditionalView](view: V)(implicit val bindingModule: BindingModule)
  extends ConditionalViewPresenter
  with Injectable {

  private val _condition = new BlockPresenter(view.setConditionStatement())
  private val _thenBlock = new BlockPresenter(view.setThenBlock())
  private val _elseBlock = new BlockPresenter(view.setElseBlock())

  view.registerViewPresenter(this)

  private[presenter] def initialize(c: Conditional): ConditionalPresenter[V] = {
    test = c.test
    then = c.then.steps
    otherwise = c.otherwise.steps
    this
  }

  private[presenter] def test: ExpressionViewPresenter = _condition

  private[presenter] def test_=(condition: Expression): Unit =
  // TODO we need to create a view depending on the expression type
    throw new NotImplementedError("condition not supported yet")

  private[presenter] def then: List[EvaluableCodeViewPresenter] = _thenBlock.steps

  private[presenter] def then_=(steps: List[EvaluableCode]): Unit = _thenBlock.steps = steps

  private[presenter] def otherwise: List[EvaluableCodeViewPresenter] = _elseBlock.steps

  private[presenter] def otherwise_=(steps: List[EvaluableCode]): Unit = _elseBlock.steps = steps

  override def compile(): Conditional =
    new Conditional(_condition.compile, _thenBlock.compile, _elseBlock.compile)

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("test", test)
      .add("then", then)
      .add("otherwise", otherwise)
      .toString

}