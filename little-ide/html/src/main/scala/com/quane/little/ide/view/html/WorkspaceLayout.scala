package com.quane.little.ide.view.html

import com.quane.little.ide.model.FunctionService
import com.quane.little.ide.presenter.FunctionDefinitionPresenter
import com.quane.little.ide.view.WorkspaceView
import com.quane.vaadin.scala.VaadinMixin
import com.vaadin.ui.Button.{ClickEvent, ClickListener}
import com.vaadin.ui.{Button, VerticalLayout, HorizontalLayout}

object WorkspaceLayout {
  val Style = "l-workspace"
}

class WorkspaceLayout
  extends HorizontalLayout
  with WorkspaceView
  with RemovableComponent
  with VaadinMixin {

  setSpacing(true)
  setStyleName(WorkspaceLayout.Style)

  addComponent(
    new VerticalLayout {
      // TODO this isn't a long term solution
      FunctionService.FunctionNames.foreach {
        function =>
          addComponent(new Button(function, new ClickListener {
            def buttonClick(event: ClickEvent) = presenter.openFunctionDefinition(function)
          }))
      }
    }
  )

  override def createFunctionDefinition(): FunctionDefinitionPresenter[_] =
    new FunctionDefinitionPresenter(add(new FunctionDefinitionLayout()))

}
