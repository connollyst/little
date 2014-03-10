package com.quane.vaadin.scala

import com.vaadin.ui.DragAndDropWrapper.DragStartMode
import com.vaadin.ui.{DragAndDropWrapper, Component}


/** A draggable UI component.
  *
  * @param c the draggable component
  * @tparam C the draggable component type
  */
class DraggableComponent[C <: Component](c: C) extends DragAndDropWrapper(c) {

  setDragStartMode(DragStartMode.WRAPPER)
  setSizeUndefined()

}