package com.quane.little.web.view

import vaadin.scala.Label

class ToolboxItem(name: String) extends DraggableComponent[Expression](null) {

  add(Label(name))

}