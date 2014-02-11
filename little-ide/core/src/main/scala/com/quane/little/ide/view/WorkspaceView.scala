package com.quane.little.ide.view


trait WorkspaceView extends View[WorkspaceViewListener]

trait WorkspaceViewListener extends ViewListener {

  def openFunctionDefinition(name: String)

}
