package com.quane.little.web.view

import vaadin.scala.CssLayout
import vaadin.scala.Label
import vaadin.scala.Measure
import vaadin.scala.Units
import vaadin.scala.VerticalLayout

object ToolboxSection {
  val Style = "l-toolbox-section"
  val StyleSeparator = Style + "-separator"

  private val ToDo = new Label
  ToDo.value = "Not Implemented"

  val Motion = new ToolboxSection() {
    add(new ToolboxItem("move forward"));
    add(new ToolboxItem("move backward"));
    add(new ToolboxSectionSeparator());
    add(new ToolboxItem("turn clockwise"));
    add(new ToolboxItem("turn counter clockwise"));
    add(new ToolboxItem("turn toward"));
    add(new ToolboxItem("turn away from"));
    add(new ToolboxSectionSeparator());
    add(new ToolboxItem("change x by"));
    add(new ToolboxItem("change y by"));
  }
  val Sensing = new ToolboxSection() {
    add(ToDo)
  }
  val Operators = new ToolboxSection() {
    add(ToDo)
  }
  val Variables = new ToolboxSection() {
    add(new ToolboxItem("my x"));
    add(new ToolboxItem("my y"));
    add(new ToolboxItem("my speed"));
    add(new ToolboxSectionSeparator());
    add(new ToolboxItem("<local variable>"));
  }
}
class ToolboxSection extends VerticalLayout {
  styleName = ToolboxSection.Style
  spacing = true
}
/**
 * Visually separates groups of related toolbox items.
 */
class ToolboxSectionSeparator extends CssLayout {
  styleName = ToolboxSection.StyleSeparator
  height = new Measure(20, Units.px)
}