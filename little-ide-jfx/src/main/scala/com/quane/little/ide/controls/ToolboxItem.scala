package com.quane.little.ide.controls


import javafx.scene.control.Label
import com.quane.little.ide.{Tool, CustomControl}
import javafx.scene.input.{ClipboardContent, TransferMode, MouseEvent}
import javafx.event.EventHandler
import com.quane.little.ide.dnd.DnDTarget
import org.eintr.loglady.Logging
import javafx.scene.layout.BorderPane
import javafx.fxml.FXML

/**
 *
 * @author Sean Connolly
 */
class ToolboxItem(tool: Tool)
  extends BorderPane
  with CustomControl
  with Serializable
  with Logging {

  def fxml: String = "ToolboxItem.fxml"

  @FXML
  private var nameLabel: Label = _

  nameLabel.setText(tool.title)

  setOnDragDetected(new EventHandler[MouseEvent] {
    def handle(event: MouseEvent) {
      log.info("Starting d&d of '" + tool.title + "'")
      val db = startDragAndDrop(TransferMode.COPY)
      val content = new ClipboardContent()
      content.putString(tool.title)
      content.put(DnDTarget.Tool, tool)
      DnDTarget.currentDnDTool = Some(tool)
      db.setContent(content)
      event.consume()
    }
  })

}
