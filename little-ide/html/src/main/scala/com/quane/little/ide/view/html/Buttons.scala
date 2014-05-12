package com.quane.little.ide.view.html

import com.vaadin.ui.{NativeButton, Button}
import com.vaadin.ui.Button.ClickEvent

object Buttons {

  def closeButton(c: RemovableComponent) = new CloseButton {
    addClickListener(new Button.ClickListener() {
      override def buttonClick(event: ClickEvent): Unit = c.removeFromParent()
    })
  }

  def blueButton(label: String, listener: () => Any) = new BlueButton(label) {
    addClickListener(new Button.ClickListener() {
      override def buttonClick(event: ClickEvent): Unit = listener()
    })
  }

}

/** A minimal looking 'X' button for closing or deleting things.
  *
  * @author Sean Connolly
  */
class CloseButton extends Button {

  setCaption("X")
  setPrimaryStyleName("l-close-btn")

}

class BlueButton(label: String) extends NativeButton(label) {

  setPrimaryStyleName("l-blue-btn")

}