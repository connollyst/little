package com.quane.little.web.view

import vaadin.scala.HorizontalLayout

object Workspace {
  val STYLE = "l-workspace"
}

class Workspace extends HorizontalLayout {

  sizeFull
  spacing = true
  styleName = Workspace.STYLE
  addComponent(new FunctionDefinition("move toward"));

}

