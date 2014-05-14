package com.quane.little.ide.view

trait ConditionalView extends ExpressionView[ConditionalViewPresenter] {

  def setConditionStatement(): BlockView

  def setThenBlock(): BlockView

  def setElseBlock(): BlockView

}

trait ConditionalViewPresenter extends ExpressionViewPresenter