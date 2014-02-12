package com.quane.little.ide.view

import com.quane.little.ide.presenter.FunctionDefinitionPresenter


trait WorkspaceView extends View[WorkspaceViewListener] {

  def createFunctionDefinition(): FunctionDefinitionPresenter[_]

}

trait WorkspaceViewListener extends ViewListener {

  def openFunctionDefinition(name: String)

}
