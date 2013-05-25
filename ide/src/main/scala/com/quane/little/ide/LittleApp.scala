package com.quane.little.ide


import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import com.google.common.eventbus.EventBus
import org.eintr.loglady.Logging
import com.quane.little.ide.layout.IDE
import scalafx.scene.{Group, Scene}
import com.quane.little.ide.events.MenuBarEventListener

/**
 *
 * @author Sean Connolly
 */
object LittleApp
  extends JFXApp
  with Logging {

  val ide = new IDE
  stage = new PrimaryStage {
    title = "little"
    width = 800
    height = 600
    scene = new Scene(new Group(ide))
  }

  // TODO replace static event bus with dependency injection
  val eventBus = new EventBus

  eventBus.register(new MenuBarEventListener(ide: IDE))

  // Set the window title in Mac
  System.setProperty("com.apple.mrj.application.apple.menu.about.name", "little")


}
