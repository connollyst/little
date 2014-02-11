package com.quane.little.ide.view.html

import com.quane.little.ide.presenter.FunctionDefinitionPresenter
import vaadin.scala.HorizontalLayout


object WorkspaceLayout {
  val Style = "l-workspace"
}

class WorkspaceLayout extends HorizontalLayout {

  sizeFull()
  spacing = true
  styleName = WorkspaceLayout.Style

  val fun = FunctionDefinitionComponent(new FunctionDefinitionPresenter, "move toward")
  add(fun)
  fun.presenter.compile()

}

