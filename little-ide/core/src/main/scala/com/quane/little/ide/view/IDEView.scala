package com.quane.little.ide.view

import com.quane.little.ide.presenter.WorkspacePresenter

trait IDEView extends View[IDEViewListener] {

  //  def setToolboxView(view: ToolboxView)

  def createWorkspace(): WorkspacePresenter[_]

}

trait IDEViewListener extends ViewListener