package com.quane.little.ide


import javafx.application.Application
import com.google.common.eventbus.EventBus
import org.eintr.loglady.Logging
import com.quane.little.ide.layout.IDE
import javafx.scene.Scene
import com.quane.little.ide.events.MenuBarEventListener
import javafx.stage.Stage

/**
 *
 * @author Sean Connolly
 */
class LittleApp
  extends Application
  with Logging {

  override def start(primaryStage: Stage) {
    val ide = new IDE
    primaryStage.setTitle("little")
    primaryStage.setWidth(800)
    primaryStage.setHeight(600)
    primaryStage.setScene(new Scene(ide))
    primaryStage.show()
    LittleApp.eventBus.register(new MenuBarEventListener(ide: IDE))
  }

}

object LittleApp {

  // TODO replace static event bus with dependency injection
  val eventBus = new EventBus

  def main(args: Array[String]) {
    Application.launch(classOf[LittleApp], args: _*)
  }

}