package com.quane.vaadin.scala

import com.vaadin.ui.DragAndDropWrapper.DragStartMode

import vaadin.scala.Component

/**
 * A draggable UI component.
 *
 * @author Sean Connolly
 *
 * @param <T>
 *         the child component type
 */
class DraggableComponent[C <: Component](c: C) extends DragAndDropWrapper(c) {

  p.setDragStartMode(DragStartMode.WRAPPER);
  p.setSizeUndefined();

}