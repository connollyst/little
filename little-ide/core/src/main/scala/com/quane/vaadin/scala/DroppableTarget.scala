package com.quane.vaadin.scala


class DroppableTarget[C <: Component](c: C) extends DragAndDropWrapper(c) {

  def dropHandler: DropHandler = p.getDropHandler

  def dropHandler_=(dropHandler: DropHandler) {
    p.setDropHandler(dropHandler)
  }

}