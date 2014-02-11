package com.quane.little.ide.view

import com.quane.little.ide.presenter.{ToolboxPresenter, WorkspacePresenter}

trait IDEView extends View[IDEViewListener] {

  def createToolbox(): ToolboxPresenter[_]

  def createWorkspace(): WorkspacePresenter[_]

}

trait IDEViewListener extends ViewListener