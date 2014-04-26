package com.quane.little.ide.view

import com.quane.little.ide.presenter.{PresenterAcceptsFunctionDefinition, FunctionDefinitionPresenter}

trait WorkspaceView extends View[WorkspaceViewPresenter] {

  def createFunctionDefinition(): FunctionDefinitionPresenter[_]

}

trait WorkspaceViewPresenter
  extends ViewPresenter
  with PresenterAcceptsFunctionDefinition