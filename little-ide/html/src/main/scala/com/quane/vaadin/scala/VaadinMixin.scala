package com.quane.vaadin.scala

import com.vaadin.ui.{ComponentContainer, Component}

/**
 *
 *
 * @author Sean Connolly
 */
trait VaadinMixin
  extends ComponentContainer {

  def add[C <: Component](c: C): C = {
    addComponent(c)
    c
  }

  def removeAll(): VaadinMixin = {
    removeAllComponents()
    this
  }

  def setStyleNames(names: String*): Unit = setStyleName(names.mkString(" "))

}
