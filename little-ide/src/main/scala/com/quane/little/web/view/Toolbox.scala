package com.quane.little.web.view

import vaadin.scala.Accordion

object Toolbox {
  val STYLE = "l-toolbox"
}

class Toolbox extends Accordion {
  sizeFull
  styleName = Toolbox.STYLE
//  addTab(LittleToolboxSection.SENSING, "Sensing");
//  addTab(LittleToolboxSection.getMotionSection(), "Motion");
//  addTab(LittleToolboxSection.OPERATORS, "Operators");
//  addTab(LittleToolboxSection.getVariablesSection(), "Variables");
  selectedTab = getTab(3).get

}