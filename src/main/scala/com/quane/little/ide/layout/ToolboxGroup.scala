package com.quane.little.ide.layout

import javafx.scene.layout.VBox
import javafx.fxml.FXML
import javafx.scene.control.Label
import com.quane.little.ide.CustomControl
import com.quane.little.language.event.LittleEvent

/**
 *
 * @author Sean Connolly
 */
class ToolboxGroup extends VBox with CustomControl {

  def fxml: String = "ToolboxGroup.fxml"

  @FXML
  private var titleLabel: Label = _

  private var tools: Class[LittleEvent] = _

  def setTitle(title: String) {
    titleLabel.setText(title)
  }

  def getTitle: String = titleLabel.getText

  def setTools(tools: Class[LittleEvent]) {
    this.tools = tools
  }

  def getTools: Class[LittleEvent] = tools

}
