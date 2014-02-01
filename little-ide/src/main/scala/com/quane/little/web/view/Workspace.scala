package com.quane.little.web.view

import vaadin.scala.HorizontalLayout

object LittleWorkspace {
  val STYLE = "l-workspace"
}

class LittleWorkspace extends HorizontalLayout {

  sizeFull
  spacing = true
  styleName = LittleWorkspace.STYLE
  addComponent(new LittleFunctionDefinition("move toward"));

}

