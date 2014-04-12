package com.quane.little.ide.presenter

import com.quane.little.ide.model.FunctionService
import com.quane.little.ide.view.{WorkspaceViewPresenter, WorkspaceView}

/** Presenter for the workspace in which the user can build code.
  *
  * @author Sean Connolly
  */
class WorkspacePresenter[V <: WorkspaceView](view: V)
  extends WorkspaceViewPresenter {

  view.registerViewPresenter(this)

  override def openFunctionDefinition(name: String) = {
    val fun = FunctionService.fetchDefinition(name)
    view.createFunctionDefinition().initialize(fun)
  }

}
