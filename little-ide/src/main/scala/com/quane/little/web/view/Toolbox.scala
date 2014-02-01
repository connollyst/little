package com.quane.little.web.view

import vaadin.scala.Accordion

object Toolbox {
  val Style = "l-toolbox"
}

class Toolbox extends Accordion {
  sizeFull
  styleName = Toolbox.Style
  addTab(ToolboxSection.Sensing, "Sensing")
  addTab(ToolboxSection.Motion, "Motion")
  addTab(ToolboxSection.Operators, "Operators")
  addTab(ToolboxSection.Variables, "Variables")
  selectedTab = getTab(1).get
}