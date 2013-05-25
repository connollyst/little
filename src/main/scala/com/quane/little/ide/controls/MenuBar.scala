package com.quane.little.ide.controls

import com.quane.little.ide.{IDEFX, CustomControl}
import javafx.fxml.FXML
import org.eintr.loglady.Logging

/**
 *
 * @author Sean Connolly
 */
class MenuBar
  extends javafx.scene.control.MenuBar
  with CustomControl
  with Logging {

  def fxml: String = "MenuBar.fxml"

  @FXML
  def compile() {
    IDEFX.compile()
  }

  @FXML
  def run() {
    IDEFX.run()
  }

}
