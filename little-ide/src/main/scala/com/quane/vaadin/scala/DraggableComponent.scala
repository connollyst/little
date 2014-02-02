package com.quane.vaadin.scala

import vaadin.scala.Component
import com.quane.vaadin.scala.DragAndDropWrapper
import com.vaadin.ui.DragAndDropWrapper.DragStartMode

/**
 * A draggable UI component.
 *
 * @author Sean Connolly
 *
 * @param <T>
 *            the child component type
 */
class DraggableComponent[C <: Component](val component: C) extends DragAndDropWrapper(component) {

  p.setDragStartMode(DragStartMode.WRAPPER);
  p.setSizeUndefined();

}