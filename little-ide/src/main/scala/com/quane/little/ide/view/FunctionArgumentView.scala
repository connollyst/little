package com.quane.little.ide.view

import com.quane.little.ide.controller.FunctionArgumentController
import vaadin.scala.Label

object FunctionArgumentView {
  private val Style = "l-function-ref-arg"
  private val DefaultName = "newArg"
}

class FunctionArgumentView(val controller: FunctionArgumentController, name: String)
  extends Label {

  def this(name: String) = {
    this(new FunctionArgumentController, name)
  }

  def this() = {
    this(FunctionArgumentView.DefaultName)
  }

  value = name
  styleName = FunctionArgumentView.Style

}