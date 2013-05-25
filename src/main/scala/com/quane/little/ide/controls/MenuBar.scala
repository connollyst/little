package com.quane.little.ide.controls

import com.quane.little.ide.{IDEFX, CustomControl}
import javafx.fxml.FXML
import org.eintr.loglady.Logging
import com.quane.little.ide.events.{MenuBarDoRunEvent, MenuBarDoCompileEvent}

/**
 *
 * @author Sean Connolly
 */
class MenuBar
  extends javafx.scene.control.MenuBar
  with CustomControl
  with Logging {

  def fxml: String = "MenuBar.fxml"

  setUseSystemMenuBar(true)

  @FXML
  def compile() {
    IDEFX.eventBus.post(new MenuBarDoCompileEvent)
  }

  @FXML
  def run() {
    IDEFX.eventBus.post(new MenuBarDoRunEvent)
  }

}