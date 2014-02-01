package com.quane.little.web.view

import vaadin.scala.HorizontalLayout

object Workspace {
  val Style = "l-workspace"
}

class Workspace extends HorizontalLayout {

  sizeFull
  spacing = true
  styleName = Workspace.Style
  addComponent(new FunctionDefinition("move toward"));

}

