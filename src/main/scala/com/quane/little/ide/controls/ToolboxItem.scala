package com.quane.little.ide.controls

import javafx.scene.layout.Pane
import javafx.fxml.FXML
import javafx.scene.control.Label
import com.quane.little.ide.CustomControl

/**
 *
 * @author Sean Connolly
 */
class ToolboxItem extends Pane with CustomControl {

  def this(label: String) = {
    this
    this.label.setText(label)
  }

  def fxml: String = "ToolboxItem.fxml"

  @FXML
  private val label: Label = null

  def getLabel: String = label.getText

  def setLabel(label: String) = this.label.setText(label)

}
