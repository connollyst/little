package com.quane.little.ide.view.html

import vaadin.scala.Label


class ToolboxItemComponent(name: String)
  extends Label {

  value = name

  // TODO should look like the real expression
  // TODO should carry the real expression as d&d payload

  //  def getStep: View[_] = {
  //    component
  //  }

}