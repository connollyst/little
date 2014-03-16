package com.quane.little.game.server

import com.smartfoxserver.v2.extensions.{ExtensionLogLevel, SFSExtension}
import com.quane.little.game.LittleGameEngine
import com.smartfoxserver.v2.mmo.MMORoom

/**
 * The SmartFox server 'extension' for the little game.
 *
 * @author Sean Connolly
 */
class LittleExtension
  extends SFSExtension {

  var gameEngine: LittleGameEngine = null

  override def init(): Unit = {
    trace("Initializing Little room..")
    if (!isMMORoom) {
      trace(ExtensionLogLevel.ERROR, "Not configured to be an MMO room!")
    }
    gameEngine = new LittleGameEngine
    gameEngine.initialize()
    trace("Initialized with "
      + gameEngine.entities.size + " items and "
      + gameEngine.players.size + " mobs."
    )
  }

  private def isMMORoom: Boolean = {
    getParentRoom.isInstanceOf[MMORoom]
  }

}
