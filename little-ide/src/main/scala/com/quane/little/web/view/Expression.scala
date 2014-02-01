package com.quane.little.web.view

import vaadin.scala.Label

class Expression(label: String) extends Label {

  def this() {
    this("TODO")
  }

  value = label

}