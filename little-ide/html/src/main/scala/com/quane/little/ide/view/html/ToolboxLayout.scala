package com.quane.little.ide.view.html

import com.quane.little.ide.view.ToolboxView
import com.vaadin.ui.Accordion

object ToolboxLayout {
  val Style = "l-toolbox"
}

class ToolboxLayout
  extends Accordion
  with ToolboxView
  with RemovableComponent {
  setSizeFull()
  setStyleName(ToolboxLayout.Style)
  addTab(ToolboxSectionComponent.Sensing, "Sensing")
  addTab(ToolboxSectionComponent.Motion, "Motion")
  addTab(ToolboxSectionComponent.Operators, "Operators")
  addTab(ToolboxSectionComponent.Variables, "Variables")
  setSelectedTab(1)
}