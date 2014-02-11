package com.quane.little.ide.view.html

import vaadin.scala.{Units, Measure, HorizontalSplitPanel}

class IDE extends HorizontalSplitPanel {

  sizeFull()
  splitPosition = new Measure(25, Units.pct)

  addComponent(new ToolboxLayout())
  addComponent(new WorkspaceLayout())

}