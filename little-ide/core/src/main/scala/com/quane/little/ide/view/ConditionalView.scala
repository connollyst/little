package com.quane.little.ide.view

import com.quane.little.ide.presenter.{BlockPresenter, ExpressionPresenter}

trait ConditionalView extends ExpressionView[ConditionalViewPresenter] {

  def setConditionStatement(): ExpressionPresenter

  def setThenBlock(): BlockPresenter[_]

  def setElseBlock(): BlockPresenter[_]

}

trait ConditionalViewPresenter extends ExpressionViewPresenter