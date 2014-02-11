package com.quane.vaadin.scala

import com.quane.vaadin.scala.mixins.DragAndDropWrapperMixin
import vaadin.scala.{AbstractComponent, Component}

package mixins {

import vaadin.scala.mixins.AbstractComponentMixin

trait DragAndDropWrapperMixin extends AbstractComponentMixin {
  self: com.vaadin.ui.DragAndDropWrapper =>
}

}

class DragAndDropWrapper[C <: Component](val component: C, override val p: com.vaadin.ui.DragAndDropWrapper with DragAndDropWrapperMixin)
  extends AbstractComponent(p) {

  def this(c: C) = {
    this(c, new com.vaadin.ui.DragAndDropWrapper(c.p) with DragAndDropWrapperMixin)
  }

}