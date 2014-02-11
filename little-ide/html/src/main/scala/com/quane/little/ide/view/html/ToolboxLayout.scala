package com.quane.little.ide.view.html

import vaadin.scala.Accordion


object ToolboxLayout {
  val Style = "l-toolbox"
}

class ToolboxLayout extends Accordion {
  sizeFull()
  styleName = ToolboxLayout.Style
  addTab(ToolboxSectionComponent.Sensing, "Sensing")
  addTab(ToolboxSectionComponent.Motion, "Motion")
  addTab(ToolboxSectionComponent.Operators, "Operators")
  addTab(ToolboxSectionComponent.Variables, "Variables")
  selectedTab = getTab(1).get
}