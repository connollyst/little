package com.quane.little.ide.view


trait GetStatementView
  extends ExpressionView[GetterViewPresenter] {

  def setName(name: String): Unit

}

trait GetterViewPresenter
  extends ExpressionViewPresenter {

  def onNameChange(name: String): Unit

}