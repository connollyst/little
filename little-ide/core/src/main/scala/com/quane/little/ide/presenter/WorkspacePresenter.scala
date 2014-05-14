package com.quane.little.ide.presenter

import com.quane.little.ide.view.{WorkspaceViewPresenter, WorkspaceView}
import com.quane.little.data.model.RecordId
import com.quane.little.data.service.{ListenerService, FunctionService}
import com.escalatesoft.subcut.inject.{Injectable, BindingModule}

/** Presenter for the workspace in which the user can build code.
  *
  * @author Sean Connolly
  */
class WorkspacePresenter[V <: WorkspaceView](view: V)(implicit val bindingModule: BindingModule)
  extends WorkspaceViewPresenter
  with Injectable {

  // TODO inject function & listener services

  view.registerViewPresenter(this)

  override def requestAddFunctionDefinition(id: RecordId, index: Int) = {
    val function = FunctionService().findDefinition(id)
    new FunctionDefinitionPresenter(view.createFunctionDefinition()).initialize(id, function)
  }

  override def requestAddEventListener(id: RecordId, index: Int) = {
    val listener = ListenerService().findListener(id)
    new EventListenerPresenter(view.createEventListener()).initialize(id, listener)
  }

  override def requestAddBlankEventListener(index: Int) =
    view.createEventListener()

}
