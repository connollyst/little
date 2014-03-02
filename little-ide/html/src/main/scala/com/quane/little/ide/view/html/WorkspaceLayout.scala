package com.quane.little.ide.view.html

import vaadin.scala._
import com.quane.little.ide.view.{WorkspaceViewListener, WorkspaceView}
import com.quane.little.ide.presenter.FunctionDefinitionPresenter
import com.quane.little.ide.model.FunctionService


object WorkspaceLayout {
  val Style = "l-workspace"
}

class WorkspaceLayout
  extends HorizontalLayout
  with WorkspaceView
  with RemovableComponent {

  spacing = true
  styleName = WorkspaceLayout.Style

  add(new VerticalLayout {
    // TODO this isn't a long term solution
    FunctionService.FunctionNames.foreach {
      function =>
        add(Button(function, {
          viewListeners.foreach {
            listener: WorkspaceViewListener =>
              listener.openFunctionDefinition(function)
          }
        }))
    }
  })

  override def createFunctionDefinition(): FunctionDefinitionPresenter[_] =
    new FunctionDefinitionPresenter(add(new FunctionDefinitionLayout()))

}

