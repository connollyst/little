package com.quane.little.ide.view.html

import vaadin.scala.Button
import com.quane.little.language.exceptions.NotImplementedError

object CloseButton {

  def apply(c: RemovableComponent) = new CloseButton {
    //    clickListeners += c.removeFromParent()
    throw new NotImplementedError("Sean, you've been drinking.")
  }

}

/** A minimal looking 'X' button for closing or deleting things.
  *
  * @author Sean Connolly
  */
class CloseButton
  extends Button {

  caption = "X"
  p.setPrimaryStyleName("l-close-btn")

}
