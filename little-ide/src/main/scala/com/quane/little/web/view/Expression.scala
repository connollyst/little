package com.quane.little.web.view

import vaadin.scala.Label

object Expression {
  val Style = "l-expression"
  val StyleButtonDelete = Style + "-btn-delete"
}

class Expression(label: String) extends Label {

  def this() {
    this("TODO")
  }

  value = label
  styleName = Expression.Style

}