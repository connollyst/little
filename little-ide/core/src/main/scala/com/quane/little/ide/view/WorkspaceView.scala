package com.quane.little.ide.view

import com.quane.little.ide.presenter.{PresenterAcceptsEventListener, PresenterAcceptsFunctionDefinition}

trait WorkspaceView extends View[WorkspaceViewPresenter] {

  def createFunctionDefinition(): FunctionDefinitionView

  def createEventListener(): EventListenerView
}

trait WorkspaceViewPresenter
  extends ViewPresenter
  with PresenterAcceptsEventListener
  with PresenterAcceptsFunctionDefinition