package com.quane.little.ide.controls

import javafx.scene.layout.Pane
import javafx.fxml.FXML
import javafx.scene.control.Label

/**
 *
 * @author Sean Connolly
 */
class ToolboxItem extends Pane with CustomControl {

  def this(label: String) = {
    this
    this.label.setText(label)
  }

  abstract def fxml: String = "ToolboxItem.fxml"

  @FXML
  private val label: Label = null

  def getLabel: String = label.getText

  def setLabel(label: String) = this.label.setText(label)

}
