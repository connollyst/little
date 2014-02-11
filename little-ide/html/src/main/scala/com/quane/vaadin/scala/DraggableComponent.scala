package com.quane.vaadin.scala

import vaadin.scala.Component
import com.vaadin.ui.DragAndDropWrapper.DragStartMode


/** A draggable UI component.
  *
  * @param c the draggable component
  * @tparam C the draggable component type
  */
class DraggableComponent[C <: Component](c: C) extends DragAndDropWrapper(c) {

  p.setDragStartMode(DragStartMode.WRAPPER)
  p.setSizeUndefined()

}