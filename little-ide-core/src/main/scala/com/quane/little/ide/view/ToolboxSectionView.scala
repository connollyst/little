package com.quane.little.ide.view

import vaadin.scala.CssLayout
import vaadin.scala.Measure
import vaadin.scala.Units
import vaadin.scala.VerticalLayout

object ToolboxSectionView {
  val Style = "l-toolbox-section"
  val StyleSeparator = Style + "-separator"

  val Motion = new ToolboxSectionView() {
    add(new ToolboxItemView("move forward"))
    add(new ToolboxItemView("move backward"))
    add(new ToolboxSectionSeparator())
    add(new ToolboxItemView("turn clockwise"))
    add(new ToolboxItemView("turn counter clockwise"))
    add(new ToolboxItemView("turn toward"))
    add(new ToolboxItemView("turn away from"))
    add(new ToolboxSectionSeparator())
    add(new ToolboxItemView("change x by"))
    add(new ToolboxItemView("change y by"))
  }
  val Sensing = new ToolboxSectionView() {
    // TODO add items
  }
  val Operators = new ToolboxSectionView() {
    // TODO add items
  }
  val Variables = new ToolboxSectionView() {
    add(new ToolboxItemView("my x"))
    add(new ToolboxItemView("my y"))
    add(new ToolboxItemView("my speed"))
    add(new ToolboxSectionSeparator())
    add(new ToolboxItemView("<local variable>"))
  }
}

class ToolboxSectionView extends VerticalLayout {
  styleName = ToolboxSectionView.Style
  spacing = true
}

/**
 * Visually separates groups of related toolbox items.
 */
class ToolboxSectionSeparator extends CssLayout {
  styleName = ToolboxSectionView.StyleSeparator
  height = new Measure(20, Units.px)
}