package com.quane.little.ide.view.html

import vaadin.scala.{VerticalLayout, Button, HorizontalLayout}
import com.quane.little.ide.view.{WorkspaceViewListener, WorkspaceView}
import com.quane.little.ide.presenter.FunctionDefinitionPresenter


object WorkspaceLayout {
  val Style = "l-workspace"
  val Functions = List("blank", "move", "stop", "turn", "voyage", "print dir")
}

class WorkspaceLayout
  extends HorizontalLayout
  with WorkspaceView
  with HtmlComponent {

  sizeFull()
  spacing = true
  styleName = WorkspaceLayout.Style

  add(new VerticalLayout {
    // TODO this isn't a long term solution
    WorkspaceLayout.Functions.foreach {
      function =>
        add(Button(
        "'" + function + "'", {
          viewListeners.foreach {
            listener: WorkspaceViewListener =>
              listener.openFunctionDefinition(function)
          }
        }))
    }
  })

  override def createFunctionDefinition(): FunctionDefinitionPresenter[_] = {
    val view = new FunctionDefinitionLayout()
    add(view)
    new FunctionDefinitionPresenter(view)
  }

}

