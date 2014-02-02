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
class DragAndDropWrapper[C <: Component](val component: C, override val p: com.vaadin.ui.DragAndDropWrapper with DragAndDropWrapperMixin)
  extends AbstractComponent(p) {

  def this(c: C) = {
    this(c, new com.vaadin.ui.DragAndDropWrapper(c.p) with DragAndDropWrapperMixin)
  }

}