package com.quane.little.ide.view.html

import vaadin.scala.{Units, Measure, HorizontalSplitPanel}
import com.quane.little.ide.view.IDEView
import com.quane.little.ide.presenter.WorkspacePresenter

class IDELayout
  extends HorizontalSplitPanel
  with IDEView {

  sizeFull()
  splitPosition = new Measure(25, Units.pct)

  var _workspace: Option[WorkspaceLayout] = None

  private def workspace_=(w: WorkspaceLayout) = {
    if (_workspace.isDefined) throw new IllegalAccessException("Workspace defined.")
    _workspace = Some(w)
  }

  private def workspace: WorkspaceLayout = {
    if (!_workspace.isDefined) throw new IllegalAccessException("No workspace defined.")
    _workspace.get
  }

  override def createWorkspace(): WorkspacePresenter[WorkspaceLayout] = {
    workspace_=(new WorkspaceLayout)
    add(workspace)
    new WorkspacePresenter(workspace)
  }

}