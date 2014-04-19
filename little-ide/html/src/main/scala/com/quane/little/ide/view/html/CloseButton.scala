package com.quane.little.ide.view.html

import com.vaadin.ui.Button
import com.vaadin.ui.Button.ClickEvent

object CloseButton {

  def apply(c: RemovableComponent) = new CloseButton {
    addClickListener(new Button.ClickListener() {
      override def buttonClick(event: ClickEvent): Unit = c.removeFromParent()
    })
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
