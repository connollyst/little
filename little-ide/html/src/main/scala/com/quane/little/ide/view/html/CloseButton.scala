package com.quane.little.ide.view.html

import vaadin.scala.Button

object CloseButton {

  def apply(c: CloseableComponent) = new CloseButton {
    clickListeners += c.close()
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
