package com.quane.little.ide.view

import com.quane.little.ide.presenter.{GamespacePresenter, ToolboxPresenter, WorkspacePresenter}

trait IDEView extends View[IDEViewPresenter] {

  def createToolbox(): ToolboxPresenter[_ <: ToolboxView]

  def createWorkspace(): WorkspacePresenter[_ <: WorkspaceView]

  def createGamespace(): GamespacePresenter[_ <: GamespaceView]

}

trait IDEViewPresenter extends ViewPresenter