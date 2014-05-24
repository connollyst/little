package com.quane.little.ide.view

import com.quane.little.language.event.EventListener
import com.quane.little.data.model.RecordId

object MockGamespaceView {
  def apply(): MockGamespaceView = new MockGamespaceView
}

/** An implementation of [[com.quane.little.ide.view.GamespaceView]] for testing
  * purposes.
  *
  * @author Sean Connolly
  */
class MockGamespaceView extends GamespaceView with MockView {

  override def createGameListener(event: EventListener, listenerId: RecordId) = Unit

}
