package com.quane.little.ide.presenter

import com.quane.little.ide.view.{GamespaceView, GamespaceViewPresenter}
import com.quane.little.data.service.ListenerService

class GamespacePresenter[V <: GamespaceView](view: V)
  extends GamespaceViewPresenter {

  view.registerViewPresenter(this)

  ListenerService().init()
  ListenerService().findByUser("connollyst") foreach {
    record =>
      view.createGameListener(record.listener, record.id)
  }

}