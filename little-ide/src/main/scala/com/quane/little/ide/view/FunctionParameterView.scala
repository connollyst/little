package com.quane.little.ide.view

import com.quane.little.ide.controller.FunctionParameterController

import vaadin.scala.Label

object FunctionParameterView {
  private val Style = "l-function-def-param"
  private val DefaultName = "newParam"
}

class FunctionParameterView(val controller: FunctionParameterController, name: String)
  extends Label {

  def this(name: String) = {
    this(new FunctionParameterController, name)
  }

  def this() = {
    this(FunctionParameterView.DefaultName)
  }

  value = name
  styleName = FunctionParameterView.Style

}