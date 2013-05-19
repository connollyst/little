package com.quane.little.ide


import java.io.IOException
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene

/**
 *
 * @author Sean Connolly
 */
object IDEFX extends JFXApp {

  val fxml = "ide.fxml";
  val resource = getClass.getResource(fxml)
  if (resource == null) {
    throw new IOException("Cannot load resource: " + fxml)
  }

  val root: Parent = FXMLLoader.load(resource)

  stage = new PrimaryStage {
    title = "little"
    width = 800
    height = 600
    scene = new Scene(root)
  }

}
