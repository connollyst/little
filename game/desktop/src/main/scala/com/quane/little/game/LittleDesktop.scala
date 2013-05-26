package com.quane.little.game

import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}

/**
 *
 * @author Sean Connolly
 */
object LittleDesktop extends App {

  val Config = new LwjglApplicationConfiguration()
  Config.title = "little"
  Config.useGL20 = true
  Config.width = 1024
  Config.height = 768

  new LittleDesktop

}

class LittleDesktop {

  new LwjglApplication(new Little(), LittleDesktop.Config)

}