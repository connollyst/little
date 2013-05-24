package com.quane.little.ide.dnd

import com.quane.little.ide.layout.Highlightable
import com.quane.little.ide.{Tool, ExpressionPaneFactory}
import javafx.scene.input.{DataFormat, TransferMode, DragEvent}
import javafx.event.EventHandler
import org.eintr.loglady.Logging
import com.quane.little.ide.layout.language.ExpressionPane
import com.quane.little.language.Expression

object DnDTarget {

  val Tool = new DataFormat("little.dnd.data.tool")

  // TODO figure out how to get data from the dragboard
  var currentDnDTool: Option[Tool] = null

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
        val tool = getPaneType(event)
        if (accepts(tool.toolType)) {
          event.acceptTransferModes(TransferMode.COPY)
        } else {
          log.error(getClass.getSimpleName + " doesn't accept this!")
        }
      }
      event.consume()
    }
  })

  private def getPaneType(event: DragEvent): Tool = {
    // val content = event.getDragboard.getContent(DnDTarget.DndItem)
    DnDTarget.currentDnDTool.get
  }

  // Handle 'Drag Entered' events
  setOnDragEntered(new EventHandler[DragEvent]() {
    def handle(event: DragEvent) {
      if (valid(event)) {
        highlight()
      }
      event.consume()
    }
  })

  // Handle 'Drag Exited' events
  setOnDragExited(new EventHandler[DragEvent]() {
    def handle(event: DragEvent) {
      unhighlight()
      event.consume()
    }
  })

  // Handle 'Drop' events
  setOnDragDropped(new EventHandler[DragEvent]() {
    def handle(event: DragEvent) {
      unhighlight()
      var success = false
      val tool = DnDTarget.currentDnDTool
      if (tool.isDefined) {
        val factory = tool.get.factory
        val panel = factory.make
        onDrop(panel)
        success = true
      }
      event.setDropCompleted(success)
      event.consume()
    }
  })

  /** Is the [[javafx.scene.input.DragEvent]] accepted here?
    *
    * @param event the drag event
    * @return if the event is valid
    */
  private def valid(event: DragEvent): Boolean = {
    event.getGestureSource != this &&
      event.getDragboard.hasContent(DnDTarget.Tool)
  }

  /** Can the getDragData be dropped here?
    *
    * @param item the drag and drop getDragData
    */
  def accepts(item: DragAndDropItem): Boolean

  /** Handle a new [[com.quane.little.ide.layout.language.ExpressionPane]]
    * being dropped.
    *
    * @param pane the pane that was dropped
    */
  def onDrop(pane: ExpressionPane[Expression[Any]])

}
