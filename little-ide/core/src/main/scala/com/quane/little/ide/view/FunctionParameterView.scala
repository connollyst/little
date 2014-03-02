package com.quane.little.ide.view

trait FunctionParameterView extends View[FunctionParameterViewPresenter] {

  def setName(name: String): Unit

}

trait FunctionParameterViewPresenter extends ViewPresenter {

  def onNameChanged(name: String): Unit

}
