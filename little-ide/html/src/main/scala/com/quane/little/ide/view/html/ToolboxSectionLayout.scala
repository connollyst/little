package com.quane.little.ide.view.html

import vaadin.scala.{Units, Measure, CssLayout, VerticalLayout}


object ToolboxSectionComponent {
  val Style = "l-toolbox-section"
  val StyleSeparator = Style + "-separator"

  val Motion = new ToolboxSectionComponent() {
    add(new ToolboxItemComponent("move forward"))
    add(new ToolboxItemComponent("move backward"))
    add(new ToolboxSectionSeparator())
    add(new ToolboxItemComponent("turn clockwise"))
    add(new ToolboxItemComponent("turn counter clockwise"))
    add(new ToolboxItemComponent("turn toward"))
    add(new ToolboxItemComponent("turn away from"))
    add(new ToolboxSectionSeparator())
    add(new ToolboxItemComponent("change x by"))
    add(new ToolboxItemComponent("change y by"))
  }
  val Sensing = new ToolboxSectionComponent() {
    // TODO add items
  }
  val Operators = new ToolboxSectionComponent() {
    // TODO add items
  }
  val Variables = new ToolboxSectionComponent() {
    add(new ToolboxItemComponent("my x"))
    add(new ToolboxItemComponent("my y"))
    add(new ToolboxItemComponent("my speed"))
    add(new ToolboxSectionSeparator())
    add(new ToolboxItemComponent("<local variable>"))
  }
}


class ToolboxSectionComponent extends VerticalLayout {
  styleName = ToolboxSectionComponent.Style
  spacing = true
}


/**
 * Visually separates groups of related toolbox items.
 */
class ToolboxSectionSeparator extends CssLayout {
  styleName = ToolboxSectionComponent.StyleSeparator
  height = new Measure(20, Units.px)
}