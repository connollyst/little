package com.quane.little.ide.controls


import javafx.scene.control.Label
import com.quane.little.ide.{Tool, CustomControl}
import javafx.scene.input.{ClipboardContent, TransferMode, MouseEvent}
import javafx.event.EventHandler
import com.quane.little.ide.dnd.DnDTarget

/**
 *
 * @author Sean Connolly
 */
class ToolboxItem(tool: Tool) extends Label with CustomControl {

  def fxml: String = "ToolboxItem.fxml"

  setText(tool.title)

  setOnDragDetected(new EventHandler[MouseEvent] {
    def handle(event: MouseEvent) {
      println("were dragging " + tool.title)
      val db = startDragAndDrop(TransferMode.COPY)
      val content = new ClipboardContent()
      content.putString(tool.title)
      content.put(DnDTarget.DndItem, tool.toolType)
      db.setContent(content)
      event.consume()
    }
  })

}
