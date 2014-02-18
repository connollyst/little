package com.quane.little.ide.view.html

import vaadin.scala.{VerticalLayout, Button, HorizontalLayout}
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

  add(new VerticalLayout {
    add(Button(
    "'move'", {
      viewListeners.foreach {
        listener: WorkspaceViewListener =>
          listener.openFunctionDefinition("move")
      }
    }))
    add(Button(
    "'stop'", {
      viewListeners.foreach {
        listener: WorkspaceViewListener =>
          listener.openFunctionDefinition("stop")
      }
    }))
  })

  override def createFunctionDefinition(): FunctionDefinitionPresenter[_] = {
    val view = new FunctionDefinitionComponent()
    add(view)
    new FunctionDefinitionPresenter(view)
  }

}

