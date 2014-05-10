package com.quane.little.ide.presenter

import com.quane.little.ide.view.{GamespaceView, GamespaceViewPresenter}

class GamespacePresenter[V <: GamespaceView](view: V)
  extends GamespaceViewPresenter {

  view.registerViewPresenter(this)

}