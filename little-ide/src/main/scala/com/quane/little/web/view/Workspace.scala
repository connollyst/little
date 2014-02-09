package com.quane.little.web.view

import vaadin.scala.HorizontalLayout
import com.quane.little.web.controller.FunctionDefinitionController

object Workspace {
  val Style = "l-workspace"
}

class Workspace extends HorizontalLayout {

  sizeFull
  spacing = true
  styleName = Workspace.Style
  addComponent(
    new FunctionDefinition(
      new FunctionDefinitionController,
      "move toward"));

}

