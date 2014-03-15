package com.quane.little.game.server

import com.smartfoxserver.v2.extensions.SFSExtension
import com.quane.little.game.LittleGameEngine
import com.quane.little.game.server.events.{CollisionHandler, MoveHandler, ReadyHandler, SpawnHandler}
import com.smartfoxserver.v2.entities.variables.{SFSRoomVariable, RoomVariable}
import com.smartfoxserver.v2.entities.User
import com.quane.little.game.entity.{Entity, Mob}
import com.smartfoxserver.v2.entities.data.{SFSObject, ISFSObject}
import scala.collection.mutable.ListBuffer
import scala.collection.JavaConversions._
import com.smartfoxserver.v2.SmartFoxServer

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
    gameEngine.initialize()
    trace("Initialized with "
      + gameEngine.entities.size + " items and "
      + gameEngine.players.size + " mobs."
    )
    gameEngine.update()
    addRequestHandler("ready", classOf[ReadyHandler])
    addRequestHandler("move", classOf[MoveHandler])
    addRequestHandler("spawn", classOf[SpawnHandler])
    addRequestHandler("collision", classOf[CollisionHandler])
  }

}
