package com.quane.vaadin.scala

import com.vaadin.ui.{DragAndDropWrapper, Component}

class DroppableTarget[C <: Component](val component: C)
  extends DragAndDropWrapper(component)