package com.quane.little.ide.dnd

import com.quane.little.ide.layout.Highlightable
import com.quane.little.ide.DropExpressionEvent
import javafx.scene.input.{DataFormat, TransferMode, DragEvent}
import javafx.event.EventHandler
import org.eintr.loglady.Logging

object DnDTarget {

  val DndItem = new DataFormat("little.dnd.item")

}

/**
 *
 * @author Sean Connolly
 */
trait DnDTarget
  extends Highlightable
  with Logging {

  setOnDragOver(new EventHandler[DragEvent]() {
    def handle(event: DragEvent) {
      if (valid(event)) {
        val data = item(event)
        if (accepts(data)) {
          event.acceptTransferModes(TransferMode.COPY)
        } else {
          log.error(getClass.getSimpleName + " doesn't accept this!")
        }
      }
      event.consume()
    }
  })

  private def item(event: DragEvent): DragAndDropItem = {
    val content = event.getDragboard.getContent(DnDTarget.DndItem)
    content match {
      case g2: DragAndDropItem => g2
      case _ => throw new ClassCastException(content.getClass.getSimpleName
        + " is not a " + classOf[DragAndDropItem].getSimpleName)
    }
  }

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
    event.getGestureSource != this && event.getDragboard.hasContent(DnDTarget.DndItem)
  }

  /** Can the item be dropped here?
    *
    * @param item the drag and drop item
    */
  def accepts(item: DragAndDropItem): Boolean

  def onDrop(event: DropExpressionEvent)
}
