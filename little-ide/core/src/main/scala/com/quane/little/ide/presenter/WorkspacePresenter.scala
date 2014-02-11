package com.quane.little.ide.presenter

import com.quane.little.ide.view.{WorkspaceViewListener, WorkspaceView}


class WorkspacePresenter[V <: WorkspaceView](val view: V)
  extends WorkspaceViewListener {

  view.addViewListener(this)

  override def openFunctionDefinition(name: String) = {
    println("TODO open function definition '" + name + "'")
  }

}
