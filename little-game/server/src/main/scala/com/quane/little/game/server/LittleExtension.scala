package com.quane.little.game.server

import com.smartfoxserver.v2.extensions.SFSExtension
import com.quane.little.game.LittleGameEngine

/**
 * The SmartFox server 'extension' for the little game.
 *
 * @author Sean Connolly
 */
class LittleExtension
  extends SFSExtension {

  var gameEngine: LittleGameEngine = null

  override def init(): Unit = {
    trace("Hello Little World!")
    gameEngine = new LittleGameEngine
    addRequestHandler("ready", classOf[ReadyHandler])
    addRequestHandler("move", classOf[MoveHandler])
    addRequestHandler("spawn", classOf[SpawnHandler])
  }

}
