package com.quane.little.ide.controls


import javafx.scene.control.Label
import com.quane.little.ide.CustomControl
import javafx.scene.input.{MouseEvent, DragEvent}
import javafx.event.EventHandler

/**
 *
 * @author Sean Connolly
 */
class ToolboxItem extends Label with CustomControl {

  def fxml: String = "ToolboxItem.fxml"

  def setName(name: String) {
    setText(name)
  }

  def getName: String = getText


  setOnDragDetected(new EventHandler[MouseEvent] {
    def handle(event: MouseEvent) {
      println("were dragging " + getName)
    }
  })

}
