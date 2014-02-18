package com.quane.little.ide.view

import com.quane.little.ide.presenter.{SetPresenter, GetPresenter, PrintPresenter}


trait BlockView extends View[BlockViewListener] {

  def createSetExpression(): SetPresenter[_]

  def createGetExpression(): GetPresenter[_]

  def createPrintStatement(): PrintPresenter[_]

}

trait BlockViewListener extends ViewListener