package com.quane.little.ide.view

import com.quane.little.ide.presenter.FunctionDefinitionPresenter

import vaadin.scala.HorizontalLayout

object WorkspaceView {
  val Style = "l-workspace"
}

class WorkspaceView extends HorizontalLayout {

  sizeFull()
  spacing = true
  styleName = WorkspaceView.Style

  val fun = FunctionDefinitionView(new FunctionDefinitionPresenter, "move toward")
  add(fun)
  fun.presenter.compile()

}

