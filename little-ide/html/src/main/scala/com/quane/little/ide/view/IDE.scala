package com.quane.little.ide.view

import vaadin.scala.{Units, Measure, HorizontalSplitPanel}

class IDE extends HorizontalSplitPanel {

  sizeFull()
  splitPosition = new Measure(25, Units.pct)

  addComponent(new ToolboxView())
  addComponent(new WorkspaceView())

}