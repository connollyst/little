package com.quane.little.ide.view

import com.quane.little.ide.controller.FunctionDefinitionController

import vaadin.scala.HorizontalLayout

object WorkspaceView {
  val Style = "l-workspace"
}

class WorkspaceView extends HorizontalLayout {

  sizeFull()
  spacing = true
  styleName = WorkspaceView.Style

  val fun = FunctionDefinitionView(new FunctionDefinitionController, "move toward")
  add(fun)
  fun.controller.compile()

}

