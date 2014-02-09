package com.quane.little.web.view

import com.quane.vaadin.scala.DraggableComponent

class ToolboxItem(name: String) extends DraggableComponent[Expression](new Expression(name)) {

  // TODO should look like the real expression
  // TODO should carry the real expression as d&d payload

  def getStep: Expression = {
    component
  }

}