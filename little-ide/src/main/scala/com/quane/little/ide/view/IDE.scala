package com.quane.little.web.view

import vaadin.scala.HorizontalSplitPanel
import vaadin.scala.Measure
import vaadin.scala.Units

class IDE extends HorizontalSplitPanel {

  sizeFull
  splitPosition = new Measure(25, Units.pct)

  addComponent(new Toolbox())
  addComponent(new Workspace())

}