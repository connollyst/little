package com.quane.little.ide.view

import com.quane.little.data.model.{ListenerId, Id}
import com.quane.little.language.event.EventListener

/** The view for the part of the IDE displaying the game and event listeners.
  *
  * @author Sean Connolly
  */
trait GamespaceView extends View[GamespaceViewPresenter] {

  def createGameListener(event: EventListener, id: ListenerId): Unit

}

trait GamespaceViewPresenter extends ViewPresenter {

  // TODO this doesn't belong here
  def newFunction(): Unit

  def newGameListener(): Unit

  def openGameListener(id: ListenerId): Unit

}