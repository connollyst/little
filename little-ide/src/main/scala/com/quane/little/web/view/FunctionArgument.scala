package com.quane.little.web.view

import vaadin.scala.Label

object FunctionArgument {
  val Style = "l-function-arg"
}
class FunctionArgument(name: String) extends Label {

  value = name
  styleName = Expression.Style
  
}