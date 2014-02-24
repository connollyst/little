package com.quane.little.ide.view.html

import com.quane.little.ide.view.html.FunctionParameterComponent._
import vaadin.scala.Label
import com.quane.little.ide.view.FunctionParameterView

object FunctionParameterComponent {
  private val Style = "l-function-def-param"
}

class FunctionParameterComponent
  extends Label
  with FunctionParameterView
  with HtmlComponent {

  styleName = Style

  def setName(name: String): Unit = {
    value = name
  }

}