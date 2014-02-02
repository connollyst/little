package com.quane.vaadin.scala

import vaadin.scala.Component
import com.vaadin.event.dd.DropHandler

class DroppableTarget[C <: Component](val component: C) extends DragAndDropWrapper(component) {

  def dropHandler: DropHandler = p.getDropHandler
  def dropHandler_=(dropHandler: DropHandler) { p.setDropHandler(dropHandler) }

}