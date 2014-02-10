package com.quane.little.ide.view

import vaadin.scala.HorizontalSplitPanel
import vaadin.scala.Measure
import vaadin.scala.Units

class IDE extends HorizontalSplitPanel {

  sizeFull()
  splitPosition = new Measure(25, Units.pct)

  addComponent(new ToolboxView())
  addComponent(new WorkspaceView())

}