package com.quane.little.ide.view.html

import vaadin.scala.AbstractTextField.TextChangeEvent
import vaadin.scala.TextField

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
    field.textChangeListeners.foreach {
      listener => listener.apply(new TextChangeEvent(field, text, 0))
    }
  }

}
