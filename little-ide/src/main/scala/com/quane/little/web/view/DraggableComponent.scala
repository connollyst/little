package com.quane.little.web.view

import com.vaadin.ui.DragAndDropWrapper
import com.vaadin.ui.DragAndDropWrapper.DragStartMode
import vaadin.scala.Component
import vaadin.scala.HorizontalLayout
import vaadin.scala.VerticalLayout

/**
 * A draggable UI component.
 *
 * @author Sean Connolly
 *
 * @param <T>
 *            the child component type
 */
class DraggableComponent[T <: Component](val component: T) extends VerticalLayout { //DragAndDropWrapper(component.p) {

  // TODO implement DraggableComponent

  //setDragStartMode(DragStartMode.WRAPPER);
  //setSizeUndefined();

}