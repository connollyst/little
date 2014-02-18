package com.quane.little.ide.view

import com.quane.little.ide.presenter.{BlockPresenter, ExpressionPresenter}

trait ConditionalView extends View[ConditionalViewListener] {

  def createTest(): ExpressionPresenter

  def createBlock(): BlockPresenter[_]

}

trait ConditionalViewListener extends ViewListener