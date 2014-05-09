package com.quane.little.ide.view

import com.quane.little.ide.presenter.{EventListenerPresenter, PresenterAcceptsEventListener, PresenterAcceptsFunctionDefinition, FunctionDefinitionPresenter}

trait WorkspaceView extends View[WorkspaceViewPresenter] {

  def createFunctionDefinition(): FunctionDefinitionPresenter[_]

  def createEventListener(): EventListenerPresenter[_]
}

trait WorkspaceViewPresenter
  extends ViewPresenter
  with PresenterAcceptsEventListener
  with PresenterAcceptsFunctionDefinition