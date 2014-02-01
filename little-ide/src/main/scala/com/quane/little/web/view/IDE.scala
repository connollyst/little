package com.quane.little.web.view

import vaadin.scala.HorizontalSplitPanel
import vaadin.scala.Measure
import vaadin.scala.Units

class IDE extends HorizontalSplitPanel {

  splitPosition = new Measure(25, Units.pct);
  sizeFull;
  //addComponent(new LittleToolbox());
  addComponent(new LittleWorkspace());

}