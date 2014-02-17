package com.quane.little.ide.view

trait FunctionParameterView extends View[FunctionParameterViewListener] {

  def setName(name: String): Unit

}

trait FunctionParameterViewListener extends ViewListener {

  def setParameterName(name: String): Unit

}
