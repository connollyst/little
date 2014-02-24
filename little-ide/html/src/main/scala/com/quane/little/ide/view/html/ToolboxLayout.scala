package com.quane.little.ide.view.html

import vaadin.scala.Accordion
import com.quane.little.ide.view.ToolboxView


object ToolboxLayout {
  val Style = "l-toolbox"
}

class ToolboxLayout
  extends Accordion
  with ToolboxView
  with HtmlComponent {
  sizeFull()
  styleName = ToolboxLayout.Style
  addTab(ToolboxSectionComponent.Sensing, "Sensing")
  addTab(ToolboxSectionComponent.Motion, "Motion")
  addTab(ToolboxSectionComponent.Operators, "Operators")
  addTab(ToolboxSectionComponent.Variables, "Variables")
  selectedTab = getTab(1).get
}