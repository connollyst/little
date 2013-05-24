package com.quane.little.ide.dnd

import com.quane.little.ide.layout.Highlightable
import com.quane.little.ide.DropExpressionEvent
import javafx.scene.input.{DataFormat, TransferMode, DragEvent}
import javafx.event.EventHandler
import org.eintr.loglady.Logging

object DnDTarget {

  val DndItem = new DataFormat("little.dnd.item")

  // TODO figure out how to get data from the dragboard
  var currentDnDItem: Option[DragAndDropItem] = null

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
        val data = getDragData(event)
        if (accepts(data)) {
          event.acceptTransferModes(TransferMode.COPY)
        } else {
          log.error(getClass.getSimpleName + " doesn't accept this!")
        }
      }
      event.consume()
    }
  })

  private def getDragData(event: DragEvent): DragAndDropItem = {
    val name = event.getDragboard.getString
    log.info("Getting d&d content for '" + name + "': "
      + DnDTarget.currentDnDItem.get)
    // val content = event.getDragboard.getContent(DnDTarget.DndItem)
    DnDTarget.currentDnDItem.get
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

  /** Can the getDragData be dropped here?
    *
    * @param item the drag and drop getDragData
    */
  def accepts(item: DragAndDropItem): Boolean

  def onDrop(event: DropExpressionEvent)
}
