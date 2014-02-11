package com.quane.little.ide.view.html

import vaadin.scala.Accordion


object ToolboxView {
  val Style = "l-toolbox"
}

class ToolboxView extends Accordion {
  sizeFull()
  styleName = ToolboxView.Style
  addTab(ToolboxSectionView.Sensing, "Sensing")
  addTab(ToolboxSectionView.Motion, "Motion")
  addTab(ToolboxSectionView.Operators, "Operators")
  addTab(ToolboxSectionView.Variables, "Variables")
  selectedTab = getTab(1).get
}