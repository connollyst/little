package com.quane.little.ide.view


class IDE extends HorizontalSplitPanel {

  sizeFull()
  splitPosition = new Measure(25, Units.pct)

  addComponent(new ToolboxView())
  addComponent(new WorkspaceView())

}