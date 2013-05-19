package com.quane.little.ide

import javafx.fxml.{FXML, Initializable}
import java.net.URL
import java.util.ResourceBundle
import javafx.scene.control.Button
import javafx.event.ActionEvent

/**
 *
 * @author Sean Connolly
 */
class IDEController extends Initializable {

  @FXML
  private val newListenerButton: Button = null
  @FXML
  private val newFunctionButton: Button = null

  @FXML
  private def newListenerButtonClicked(event: ActionEvent) {
    println("gotcha: " + newListenerButton)
  }

  @FXML
  private def newFunctionButtonClicked(event: ActionEvent) {
    println("gotcha: " + newFunctionButton)
  }

  def initialize(p1: URL, p2: ResourceBundle) {}

}
