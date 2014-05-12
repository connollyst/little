package com.quane.little.ide.presenter

import com.quane.little.ide.view.{GamespaceView, GamespaceViewPresenter}
import com.quane.little.data.service.ListenerService
import com.quane.little.data.model.RecordId

class GamespacePresenter[V <: GamespaceView](view: V)
  extends GamespaceViewPresenter
  with HasWorkspace {

  view.registerViewPresenter(this)

  ListenerService().init()
  ListenerService().findByUser("connollyst") foreach {
    record =>
      view.createGameListener(record.listener, record.id)
  }

  override def newGameListener() = workspace.requestAddBlankEventListener(0)

  override def openGameListener(listenerId: RecordId) = workspace.requestAddEventListener(listenerId, 0)


}