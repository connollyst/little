package com.quane.little.ide.view.html

import com.quane.little.ide.model.FunctionService
import com.quane.little.ide.presenter.FunctionDefinitionPresenter
import com.quane.little.ide.view.WorkspaceView
import com.quane.vaadin.scala.VaadinMixin
import com.vaadin.ui.Button.{ClickEvent, ClickListener}
import com.vaadin.ui.{Button, VerticalLayout, HorizontalLayout}
import com.quane.little.ide.presenter.command.{AddFunctionDefinitionCommand, IDECommandExecutor}

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
      FunctionService().findByUser("connollyst") foreach {
        function =>
          addComponent(new Button(
            function.definition.name,
            new ClickListener {
              def buttonClick(event: ClickEvent) =
                IDECommandExecutor.execute(
                  new AddFunctionDefinitionCommand(presenter, function.id)
                )
            }
          ))
      }
    }
  )

  override def createFunctionDefinition(): FunctionDefinitionPresenter[_] =
    new FunctionDefinitionPresenter(add(new FunctionDefinitionLayout()))

}
