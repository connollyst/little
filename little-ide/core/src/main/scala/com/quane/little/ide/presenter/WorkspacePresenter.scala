package com.quane.little.ide.presenter

import com.quane.little.ide.view.{WorkspaceViewPresenter, WorkspaceView}
import com.quane.little.data.model.RecordId
import com.quane.little.data.service.FunctionService

/** Presenter for the workspace in which the user can build code.
  *
  * @author Sean Connolly
  */
class WorkspacePresenter[V <: WorkspaceView](view: V)
  extends WorkspaceViewPresenter {

  view.registerViewPresenter(this)

  override def requestAddFunctionDefinition(id: RecordId, index: Int): Unit = {
    val fun = FunctionService().findDefinition(id)
    view.createFunctionDefinition().initialize(id, fun)
  }

}
