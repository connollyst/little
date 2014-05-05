package com.quane.little.ide.view.html

import com.vaadin.ui.{CssLayout, VerticalLayout}
import com.vaadin.server.Sizeable

object ToolboxSectionComponent {
  val Style = "l-toolbox-section"
  val StyleSeparator = Style + "-separator"
}

class ToolboxSectionComponent
  extends VerticalLayout {

  setStyleName(ToolboxSectionComponent.Style)
  setSpacing(true)

}

/**
 * Visually separates groups of related toolbox items.
 */
class ToolboxSectionSeparator
  extends CssLayout {

  setStyleName(ToolboxSectionComponent.StyleSeparator)
  setHeight(20, Sizeable.Unit.PIXELS)

}