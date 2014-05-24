package com.quane.little.ide.view

import com.quane.little.language.event.Event.Event

/** An implementation of [[com.quane.little.ide.view.EventListenerView]] for
  * testing purposes.
  *
  * @author Sean Connolly
  */
class MockEventListenerView extends EventListenerView with MockView {

  override def createBlock() = new MockBlockView

  override def setEvent(event: Event) = Unit

}
