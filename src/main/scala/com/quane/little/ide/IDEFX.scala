package com.quane.little.ide


import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.Parent
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import org.eintr.loglady.Logging
import com.quane.little.ide.layout.{Toolbox, Workspace}
import com.quane.little.ide.controls.MenuBar

/**
 *
 * @author Sean Connolly
 */
object IDEFX
  extends JFXApp
  with Logging {

  val fxml = "ide.fxml"

  @FXML
  private var menubar: MenuBar = _
  @FXML
  private var workspace: Workspace = _
  @FXML
  private var toolbox: Toolbox = _

  val resource = getClass.getResource(fxml)
  val root: Parent = FXMLLoader.load(resource)
  stage = new PrimaryStage {
    title = "little"
    width = 800
    height = 600
    scene = new Scene(root)
  }

  // Set the window title in Mac
  System.setProperty("com.apple.mrj.application.apple.menu.about.name", "little");

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

