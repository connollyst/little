package com.quane.little.ide.view.html

import com.vaadin.ui.{CssLayout, VerticalLayout}
import com.vaadin.server.Sizeable
import com.quane.vaadin.scala.VaadinMixin

object ToolboxSectionComponent {
  val Style = "l-toolbox-section"
  val StyleSeparator = Style + "-separator"
}

class ToolboxSectionComponent
  extends VerticalLayout
  with VaadinMixin {

  setStyleName(ToolboxSectionComponent.Style)
  setSpacing(true)

  // TODO doesn't take separator into account
  def count: Int = getComponentCount

}

/**
 * Visually separates groups of related toolbox items.
 */
class ToolboxSectionSeparator
  extends CssLayout {

  setStyleName(ToolboxSectionComponent.StyleSeparator)
  setHeight(20, Sizeable.Unit.PIXELS)

}