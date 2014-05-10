package com.quane.little.ide.presenter

import com.quane.little.ide.view.WorkspaceView

trait HasWorkspace {

  var _workspace: Option[WorkspacePresenter[_ <: WorkspaceView]] = None

  def workspace: WorkspacePresenter[_ <: WorkspaceView] =
    _workspace match {
      case Some(w) => w
      case None => throw new IllegalAccessException("No workspace presenter.")
    }

  def workspace_=(w: WorkspacePresenter[_ <: WorkspaceView]): Unit = {
    if (_workspace.isDefined) throw new IllegalAccessException("Workspace already defined.")
    _workspace = Some(w)
  }

}
