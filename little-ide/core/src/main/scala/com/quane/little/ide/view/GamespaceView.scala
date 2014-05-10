package com.quane.little.ide.view

import com.quane.little.language.event.EventListener
import com.quane.little.data.model.RecordId

/** The view for the part of the IDE displaying the game and event listeners.
  *
  * @author Sean Connolly
  */
trait GamespaceView extends View[GamespaceViewPresenter] {

  def createGameListener(event: EventListener, listenerId: RecordId): Unit

}

trait GamespaceViewPresenter extends ViewPresenter {

  def openGameListener(listenerId: RecordId): Unit

}