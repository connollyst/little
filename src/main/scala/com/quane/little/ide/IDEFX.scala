package com.quane.little.ide


import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import com.google.common.eventbus.EventBus
import org.eintr.loglady.Logging
import com.quane.little.ide.layout.{MenuBarListener, IDE}
import scalafx.scene.{Group, Scene}

/**
 *
 * @author Sean Connolly
 */
object IDEFX
  extends JFXApp
  with Logging {

  stage = new PrimaryStage {
    title = "little"
    width = 800
    height = 600
    scene = new Scene(new Group(new IDE))
  }

  // TODO replace static event bus with dependency injection
  val eventBus = new EventBus

  eventBus.register(new MenuBarListener)

  // Set the window title in Mac
  System.setProperty("com.apple.mrj.application.apple.menu.about.name", "little");

}
