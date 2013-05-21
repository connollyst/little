package com.quane.little.ide.dnd

import com.quane.little.ide.layout.Highlightable
import com.quane.little.ide.DropExpressionEvent
import javafx.scene.input.DragEvent
import javafx.event.EventHandler

/**
 *
 * @author Sean Connolly
 */
trait DnDTarget extends Highlightable {

  /** Returns true/false if the specified item can/cannot be dropped here,
    * respectively.
    *
    * @param item
      * the drag and drop item
    */
  def accepts(item: DragAndDropItem): Boolean;

  def onDrop(event: DropExpressionEvent): Unit;

  setOnDragEntered(new EventHandler[DragEvent]() {
    def handle(event: DragEvent) {
      highlight
    }
  })

  setOnDragExited(new EventHandler[DragEvent]() {
    def handle(event: DragEvent) {
      unhighlight
    }
  })

  setOnDragDropped(new EventHandler[DragEvent]() {
    def handle(event: DragEvent) {
      unhighlight
      //      onDrop(event)
    }
  })

  //    case event: MouseEntered =>
  //      IDE.eventBus.post(event)
  //    case event: MouseExited =>
  //      IDE.eventBus.post(event)
  //    case event: DragOverEvent =>
  //      highlight
  //    case event: DragOutEvent =>
  //      unhighlight
  //    case event: DropExpressionEvent =>
  //      unhighlight
  //      onDrop(event)

}
