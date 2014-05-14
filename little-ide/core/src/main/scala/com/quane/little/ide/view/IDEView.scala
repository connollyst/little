package com.quane.little.ide.view

trait IDEView extends View[IDEViewPresenter] {

  def createToolbox(): ToolboxView

  def createWorkspace(): WorkspaceView

  def createGamespace(): GamespaceView

}

trait IDEViewPresenter extends ViewPresenter