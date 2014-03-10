package com.quane.little.ide.view.html

import com.vaadin.ui.TextField
import com.vaadin.event.FieldEvents.{TextChangeListener, TextChangeEvent}
import collection.JavaConversions._

/** Utilities for simulating user interaction with the UI components.
  *
  * @author Sean Connolly
  */
object InteractionSimulator {

  /** Simulate a user changing the value of a text field.
    *
    * @param field the text field to interact with
    * @param text the new text value
    */
  private[html] def setText(field: TextField, text: String): Unit = {
    field.getListeners(classOf[TextChangeEvent]) foreach {
      case listener: TextChangeListener =>
        listener.textChange(new MockTextChangeEvent(field, text))
    }
  }

  private class MockTextChangeEvent(field: TextField, text: String)
    extends TextChangeEvent(field) {

    def getText: String = text

    def getCursorPosition: Int = 0

  }

}
