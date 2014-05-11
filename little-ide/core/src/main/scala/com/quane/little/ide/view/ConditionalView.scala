package com.quane.little.ide.view

import com.quane.little.ide.presenter.BlockPresenter

trait ConditionalView extends ExpressionView[ConditionalViewPresenter] {

  def setConditionStatement(): ExpressionViewPresenter

  def setThenBlock(): BlockPresenter[_]

  def setElseBlock(): BlockPresenter[_]

}

trait ConditionalViewPresenter extends ExpressionViewPresenter