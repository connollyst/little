package com.quane.vaadin.scala

import com.vaadin.ui.{DragAndDropWrapper, Component}
import com.vaadin.event.dd.DropHandler

class DroppableTarget[C <: Component](val component: C)
  extends DragAndDropWrapper(component) {

  def this(component: C, handler: DropHandler) = {
    this(component)
    setDropHandler(handler)
  }

}