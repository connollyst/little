package com.quane.vaadin.scala

import vaadin.scala.AbstractComponent
import vaadin.scala.mixins.AbstractComponentMixin
import com.quane.vaadin.scala.mixins.DragAndDropWrapperMixin
import vaadin.scala.TextField
import vaadin.scala.Button
import vaadin.scala.server.ThemeResource
import vaadin.scala.Notification
import vaadin.scala.Component
import com.vaadin.ui.Label

package mixins {
  trait DragAndDropWrapperMixin extends AbstractComponentMixin { self: com.vaadin.ui.DragAndDropWrapper => }
}
class DragAndDropWrapper(override val p: com.vaadin.ui.DragAndDropWrapper with DragAndDropWrapperMixin = new com.vaadin.ui.DragAndDropWrapper(null) with DragAndDropWrapperMixin)
  extends AbstractComponent(p) {

  def this(component: Component) {
    this(new com.vaadin.ui.DragAndDropWrapper(component.p) with DragAndDropWrapperMixin)
  }

}