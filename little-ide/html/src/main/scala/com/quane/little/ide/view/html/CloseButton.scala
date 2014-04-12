package com.quane.little.ide.view.html

import com.vaadin.ui.Button

object CloseButton {

  def apply(c: RemovableComponent) = new CloseButton {
    //    clickListeners += c.removeFromParent()
    println("Sean, you've been drinking.")
  }

}

/** A minimal looking 'X' button for closing or deleting things.
  *
  * @author Sean Connolly
  */
class CloseButton
  extends Button {

  setCaption("X")
  setPrimaryStyleName("l-close-btn")

}
