package com.quane.little.ide.view.html

import vaadin.scala.{Button, HorizontalLayout}
import com.quane.little.ide.view.{WorkspaceViewListener, WorkspaceView}


object WorkspaceLayout {
  val Style = "l-workspace"
}

class WorkspaceLayout
  extends HorizontalLayout
  with WorkspaceView {

  sizeFull()
  spacing = true
  styleName = WorkspaceLayout.Style

  add(Button(
  "Open 'move toward'", {
    viewListeners.foreach {
      listener: WorkspaceViewListener =>
        listener.openFunctionDefinition("move toward")
    }
  }))

  //  val fun = FunctionDefinitionComponent(new FunctionDefinitionPresenter, "move toward")
  //  add(fun)
  //  fun.presenter.compile()

}

