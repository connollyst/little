package com.quane.little.web.view

import com.quane.little.language.Runtime
import com.quane.little.web.controller.FunctionDefinitionController

import vaadin.scala.HorizontalLayout

object Workspace {
  val Style = "l-workspace"
}

class Workspace extends HorizontalLayout {

  sizeFull
  spacing = true
  styleName = Workspace.Style

  val fun = new FunctionDefinition(new FunctionDefinitionController, "move toward")
  add(fun)
  fun.controller.compile(new Runtime)

}

