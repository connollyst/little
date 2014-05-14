package com.quane.little.ide.view.html

import com.quane.little.ide.presenter.EventListenerPresenter
import com.quane.little.ide.view.{FunctionDefinitionView, EventListenerView, WorkspaceView}
import com.quane.vaadin.scala.{DroppableTarget, VaadinMixin}
import com.vaadin.ui.{AbsoluteLayout, TabSheet}
import com.vaadin.event.dd.{DragAndDropEvent, DropHandler}
import com.vaadin.event.dd.acceptcriteria.AcceptAll
import com.quane.little.ide.view.html.dnd.CodeTransferable
import com.quane.little.ide.presenter.command.{AddEventListenerCommand, AddFunctionDefinitionCommand, IDECommandExecutor}
import com.quane.little.data.model.CodeCategory

object WorkspaceTabSheet {
  val Style = "l-workspace"
  val StyleBody = Style + "-body"
}

/** The workspace is the tabletop on which the user develops their code.
  *
  * @author Sean Connolly
  */
class WorkspaceTabSheet
  extends TabSheet
  with WorkspaceView
  with VaadinMixin {

  setSizeFull()
  setStyleName(WorkspaceTabSheet.Style)

  override def createEventListener(): EventListenerView = {
    val view = new EventListenerLayout()
    val workspace = createWorkspace()
    workspace.add(view)
  }

  override def createFunctionDefinition(): FunctionDefinitionView = {
    val view = new FunctionDefinitionLayout()
    val workspace = createWorkspace()
    workspace.add(view)
  }

  private def createWorkspace(): WorkspaceLayout with VaadinMixin = {
    val layout = new WorkspaceLayout with VaadinMixin
    val workspace = new DroppableTarget(layout)
    workspace.setDropHandler(new WorkspaceDropHandler(this))
    workspace.setSizeFull()
    addTab(workspace, "new tab").setClosable(true)
    setSelectedTab(workspace)
    layout
  }

}

class WorkspaceLayout
  extends AbsoluteLayout {

  setSizeFull()
  setStyleName(WorkspaceTabSheet.StyleBody)

}

/**
 * Handler for drag &amp; drop events onto the workspace.
 *
 * @param workspace the workspace to interact with
 */
class WorkspaceDropHandler(workspace: WorkspaceTabSheet) extends DropHandler {

  override def getAcceptCriterion = AcceptAll.get()

  override def drop(event: DragAndDropEvent) =
    event.getTransferable match {
      case transferable: CodeTransferable if transferable.category == CodeCategory.Function =>
        IDECommandExecutor.execute(
          new AddFunctionDefinitionCommand(workspace.presenter, transferable.codeId)
        )
      case transferable: CodeTransferable if transferable.category == CodeCategory.EventListener =>
        IDECommandExecutor.execute(
          new AddEventListenerCommand(workspace.presenter, transferable.codeId)
        )
      case _ =>
        throw new IllegalAccessException("Drop not supported: " + event.getTransferable)
    }

}