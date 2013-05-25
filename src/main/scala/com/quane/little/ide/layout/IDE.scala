package com.quane.little.ide.layout

import com.quane.little.ide.{GlassGameFrame, CustomControl}
import javafx.fxml.FXML
import com.quane.little.ide.controls.MenuBar
import org.eintr.loglady.Logging
import javafx.scene.Group

/**
 *
 * @author Sean Connolly
 */
class IDE
  extends Group
  with CustomControl
  with Logging {

  def fxml: String = "IDE.fxml"

  @FXML
  private var menubar: MenuBar = _
  @FXML
  private var workspace: Workspace = _
  @FXML
  private var toolbox: Toolbox = _

  def compile() {
    log.info("Compiling..")
    workspace.compileAllFunctions
    workspace.compileAllListeners
  }

  def run() {
    log.info("Compiling..")
    val eventListeners = workspace.compileAllListeners
    log.info("Running..")
    new GlassGameFrame().run(eventListeners)
  }

}

class MenuBarListener {

  //  @Subscribe
  //  def compileEvent(event: DoCompileEvent) {
  //    IDEFX.compile()
  //  }

  //  @Subscribe
  //  def runEvent(event: DoRunEvent) {
  //    IDEFX.run();
  //  }

}
