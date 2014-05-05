package com.quane.little.ide.view.html

import com.quane.little.ide.view.IDEView
import com.quane.little.ide.presenter.{ToolboxPresenter, WorkspacePresenter}
import com.vaadin.ui._
import com.vaadin.server.Sizeable
import scala.Some

class IDELayout
  extends HorizontalSplitPanel
  with IDEView
  with RemovableComponent {

  setSizeFull()
  setSplitPosition(25, Sizeable.Unit.PERCENTAGE)

  // createGameWindow()

  private def createGameWindow() = {
    val subWindow = new GameWindow()
    UI.getCurrent.addWindow(subWindow)
  }

  override def createToolbox() = {
    toolbox = new ToolboxLayout
    setFirstComponent(toolbox)
    new ToolboxPresenter(toolbox)
  }

  override def createWorkspace() = {
    workspace = new WorkspaceLayout
    setSecondComponent(workspace)
    new WorkspacePresenter(workspace)
  }

  var _toolbox: Option[ToolboxLayout] = None
  var _workspace: Option[WorkspaceLayout] = None

  private def toolbox_=(t: ToolboxLayout) = {
    if (_toolbox.isDefined) throw new IllegalAccessException("Toolbox already defined.")
    _toolbox = Some(t)
  }

  private def toolbox: ToolboxLayout = {
    _toolbox match {
      case Some(t) => t
      case _ => throw new IllegalAccessException("No toolbox defined.")
    }
  }

  private def workspace_=(w: WorkspaceLayout) = {
    if (_workspace.isDefined) throw new IllegalAccessException("Workspace already defined.")
    _workspace = Some(w)
  }

  private def workspace: WorkspaceLayout = {
    _workspace match {
      case Some(t) => t
      case _ => throw new IllegalAccessException("No workspace defined.")
    }
  }

}