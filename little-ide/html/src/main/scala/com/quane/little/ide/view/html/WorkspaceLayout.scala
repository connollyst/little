package com.quane.little.ide.view.html

import com.quane.little.ide.presenter.FunctionDefinitionPresenter
import com.quane.little.ide.view.{WorkspaceViewPresenter, WorkspaceView}
import com.quane.vaadin.scala.{DroppableTarget, VaadinMixin}
import com.vaadin.ui.{CssLayout, HorizontalLayout}
import com.vaadin.event.dd.{DragAndDropEvent, DropHandler}
import com.vaadin.event.dd.acceptcriteria.AcceptAll
import com.quane.little.ide.view.html.dnd.FunctionTransferable
import com.quane.little.ide.presenter.command.{AddFunctionDefinitionCommand, IDECommandExecutor}

object WorkspaceLayout {
  val Style = "l-workspace"
}

/** The workspace is the tabletop on which the user develops their code.
  *
  * @author Sean Connolly
  */
class WorkspaceLayout
  extends CssLayout
  with WorkspaceView
  with RemovableComponent
  with VaadinMixin {

  setSizeFull()
  setStyleName(WorkspaceLayout.Style)
  val workspace = new DroppableTarget(new HorizontalLayout())
  workspace.component.setSpacing(true)
  workspace.setDropHandler(new WorkspaceDropHandler(presenter))
  workspace.setSizeFull()
  add(workspace)

  override def createFunctionDefinition(): FunctionDefinitionPresenter[_] = {
    val view = new FunctionDefinitionLayout()
    workspace.component.addComponent(view)
    new FunctionDefinitionPresenter(view)
  }

}

/**
 * Handler for drag &amp; drop events onto the workspace.
 *
 * @param presenter the workspace presenter to interact with
 */
class WorkspaceDropHandler(presenter: WorkspaceViewPresenter) extends DropHandler {

  override def getAcceptCriterion = AcceptAll.get()

  override def drop(event: DragAndDropEvent) =
    event.getTransferable match {
      case transferable: FunctionTransferable =>
        IDECommandExecutor.execute(
          new AddFunctionDefinitionCommand(presenter, transferable.functionId)
        )
    }

}