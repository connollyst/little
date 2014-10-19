package com.quane.little.ide.presenter

import com.quane.little.data.model.CodeCategory._
import com.quane.little.ide.view.{WorkspaceViewPresenter, WorkspaceView}
import com.quane.little.data.model.{FunctionId, ListenerId, Id}
import com.quane.little.data.service.{ListenerService, FunctionService}
import com.escalatesoft.subcut.inject.{Injectable, BindingModule}

/** Presenter for the workspace in which the user can build code.
  *
  * @author Sean Connolly
  */
class WorkspacePresenter[V <: WorkspaceView](view: V)(implicit val bindingModule: BindingModule)
  extends WorkspaceViewPresenter
  with Injectable {

  view.registerViewPresenter(this)

  private val functionService = inject[FunctionService]
  private val listenerService = inject[ListenerService]

  override def requestAddFunctionDefinition(id: FunctionId, index: Int = 0) = {
    val function = functionService.findDefinition(id)
    new FunctionDefinitionPresenter(view.createFunctionDefinition()).initialize(id, function)
  }

  override def requestAddEventListener(id: ListenerId, index: Int = 0) = {
    val listener = listenerService.findListener(id)
    new EventListenerPresenter(view.createEventListener()).initialize(id, listener)
  }

  override def requestAddBlankFunctionDefinition(category: CodeCategory, index: Int = 0) =
    new FunctionDefinitionPresenter(view.createFunctionDefinition())

  override def requestAddBlankEventListener(index: Int = 0) =
    new EventListenerPresenter(view.createEventListener())

}
