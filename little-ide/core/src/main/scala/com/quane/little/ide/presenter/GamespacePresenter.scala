package com.quane.little.ide.presenter

import com.escalatesoft.subcut.inject.{BindingModule, Injectable}
import com.quane.little.data.model.{CodeCategory, ListenerId}
import com.quane.little.data.service.ListenerService
import com.quane.little.ide.view.{GamespaceView, GamespaceViewPresenter}

class GamespacePresenter[V <: GamespaceView](view: V)(implicit val bindingModule: BindingModule)
  extends GamespaceViewPresenter
  with HasWorkspace
  with Injectable {

  view.registerViewPresenter(this)

  private val listenerService = inject[ListenerService]

  listenerService.init()
  listenerService.findByUser("connollyst") foreach {
    record =>
      view.createGameListener(record.listener, record.id)
  }

  // TODO this doesn't belong in the gamespace
  override def newFunction() = workspace.requestAddBlankFunctionDefinition(CodeCategory.Misc)

  override def newGameListener() = workspace.requestAddBlankEventListener()

  override def openGameListener(id: ListenerId) = workspace.requestAddEventListener(id)


}