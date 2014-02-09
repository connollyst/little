package com.quane.little.ide.view

import vaadin.scala.Label

object FunctionArgumentView {
  val Style = "l-function-def-arg"
}

class FunctionArgumentView(name: String) extends Label {

  value = name
  styleName = ExpressionView.Style

}