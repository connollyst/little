package com.quane.little.ide.view


trait FunctionArgumentView extends View[FunctionArgumentViewListener] {

  def setName(name: String): Unit

  def setValue(value: String): Unit

}

trait FunctionArgumentViewListener extends ViewListener
