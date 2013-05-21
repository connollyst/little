package com.quane.little.ide.dnd

import com.quane.little.ide.layout.Highlightable
import com.quane.little.ide.DropExpressionEvent
import javafx.scene.input.{TransferMode, DragEvent}
import javafx.event.EventHandler

/**
 *
 * @author Sean Connolly
 */
trait DnDTarget extends Highlightable {


  /** Can the item be dropped here?
    *
    * @param item the drag and drop item
    */
  def accepts(item: DragAndDropItem): Boolean

  def onDrop(event: DropExpressionEvent)

  setOnDragOver(new EventHandler[DragEvent]() {
    def handle(event: DragEvent) {
      if (valid(event)) {
        event.acceptTransferModes(TransferMode.COPY)
      }
      event.consume()
    }
  })

  setOnDragEntered(new EventHandler[DragEvent]() {
    def handle(event: DragEvent) {
      if (valid(event)) {
        highlight()
      }
      event.consume()
    }
  })

  setOnDragExited(new EventHandler[DragEvent]() {
    def handle(event: DragEvent) {
      unhighlight()
      event.consume()
    }
  })

  setOnDragDropped(new EventHandler[DragEvent]() {
    def handle(event: DragEvent) {
      unhighlight()
      val db = event.getDragboard
      var success = false
      if (db.hasString) {
        // onDrop(event)
        success = true
      }
      event.setDropCompleted(success)
      event.consume()
    }
  })

  private def valid(event: DragEvent): Boolean = {
    event.getGestureSource != this && event.getDragboard.hasString
  }

}
