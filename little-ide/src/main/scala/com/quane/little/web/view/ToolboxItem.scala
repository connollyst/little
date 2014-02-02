package com.quane.little.web.view

import vaadin.scala.Label
import com.quane.vaadin.scala.DraggableComponent

class ToolboxItem(name: String) extends DraggableComponent[Expression](new Expression(name)) {

}