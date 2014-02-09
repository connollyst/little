package com.quane.little.ide.view

import com.quane.vaadin.scala.DraggableComponent

class ToolboxItem(name: String) extends DraggableComponent[Expression](new Expression(name)) {

  // TODO should look like the real expression
  // TODO should carry the real expression as d&d payload

  def getStep: Expression = {
    component
  }

}