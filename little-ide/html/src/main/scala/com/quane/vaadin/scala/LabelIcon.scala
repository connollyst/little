package com.quane.vaadin.scala

import com.vaadin.ui.Label
import com.vaadin.shared.ui.label.ContentMode
import com.porotype.iconfont.FontAwesome.{Icon, IconVariant}

/** Utilities for creating FontAwesome icons.
  *
  * @author Sean Connolly
  */
object LabelIcon {

  def apply(icon: Icon): Label = new Label(icon.toString, ContentMode.HTML)

  def apply(icon: Icon, variants: IconVariant*): Label = {
    val label = new Label(icon.variant(variants: _*), ContentMode.HTML)
    label.setSizeUndefined()
    label
  }

}
