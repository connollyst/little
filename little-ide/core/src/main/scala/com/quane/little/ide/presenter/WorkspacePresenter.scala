package com.quane.little.ide.presenter

import com.quane.little.ide.view.{WorkspaceViewListener, WorkspaceView}


class WorkspacePresenter[V <: WorkspaceView](val view: V)
  extends WorkspaceViewListener {

  view.addViewListener(this)

  override def openFunctionDefinition(name: String) = {
    println("TODO open function definition '" + name + "'!!!")
    // TODO find function definition
    // Create function definition view
    val presenter = view.createFunctionDefinition()
    // TODO pass in function definition
  }

}
