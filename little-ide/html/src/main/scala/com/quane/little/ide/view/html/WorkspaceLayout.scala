package com.quane.little.ide.view.html

import vaadin.scala.{Button, HorizontalLayout}
import com.quane.little.ide.view.{WorkspaceViewListener, WorkspaceView}
import com.quane.little.ide.presenter.FunctionDefinitionPresenter


object WorkspaceLayout {
  val Style = "l-workspace"
}

class WorkspaceLayout
  extends HorizontalLayout with WorkspaceView {

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


  def createFunctionDefinition(): FunctionDefinitionPresenter[_] = {
    val view = new FunctionDefinitionComponent()
    add(view)
    FunctionDefinitionPresenter("move forward", view)
  }

}

