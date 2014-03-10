package com.quane.little.ide.view.html

import com.vaadin.ui.{CssLayout, VerticalLayout}
import com.vaadin.server.Sizeable

object ToolboxSectionComponent {
  val Style = "l-toolbox-section"
  val StyleSeparator = Style + "-separator"

  val Motion = new ToolboxSectionComponent() {
    addComponent(new ToolboxItemComponent("move forward"))
    addComponent(new ToolboxItemComponent("move backward"))
    addComponent(new ToolboxSectionSeparator())
    addComponent(new ToolboxItemComponent("turn clockwise"))
    addComponent(new ToolboxItemComponent("turn counter clockwise"))
    addComponent(new ToolboxItemComponent("turn toward"))
    addComponent(new ToolboxItemComponent("turn away from"))
    addComponent(new ToolboxSectionSeparator())
    addComponent(new ToolboxItemComponent("change x by"))
    addComponent(new ToolboxItemComponent("change y by"))
  }
  val Sensing = new ToolboxSectionComponent() {
    // TODO add items
  }
  val Operators = new ToolboxSectionComponent() {
    // TODO add items
  }
  val Variables = new ToolboxSectionComponent() {
    addComponent(new ToolboxItemComponent("my x"))
    addComponent(new ToolboxItemComponent("my y"))
    addComponent(new ToolboxItemComponent("my speed"))
    addComponent(new ToolboxSectionSeparator())
    addComponent(new ToolboxItemComponent("<local variable>"))
  }
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