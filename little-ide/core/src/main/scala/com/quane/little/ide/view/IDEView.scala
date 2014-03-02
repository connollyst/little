package com.quane.little.ide.view

import com.quane.little.ide.presenter.{ToolboxPresenter, WorkspacePresenter}

trait IDEView extends View[IDEViewPresenter] {

  def createToolbox(): ToolboxPresenter[_]

  def createWorkspace(): WorkspacePresenter[_]

}

trait IDEViewPresenter extends ViewPresenter