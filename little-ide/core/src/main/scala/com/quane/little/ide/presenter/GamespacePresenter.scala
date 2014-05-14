package com.quane.little.ide.presenter

import com.quane.little.ide.view.{GamespaceView, GamespaceViewPresenter}
import com.quane.little.data.service.ListenerService
import com.quane.little.data.model.RecordId
import com.escalatesoft.subcut.inject.{Injectable, BindingModule}

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

  override def newGameListener() = workspace.requestAddBlankEventListener(0)

  override def openGameListener(listenerId: RecordId) = workspace.requestAddEventListener(listenerId, 0)


}