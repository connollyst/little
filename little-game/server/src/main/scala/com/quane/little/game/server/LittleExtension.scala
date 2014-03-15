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
      + gameEngine.entities.length + " items and "
      + gameEngine.players.length + " mobs."
    )
    gameEngine.update()
    addRequestHandler("ready", classOf[ReadyHandler])
    addRequestHandler("move", classOf[MoveHandler])
    addRequestHandler("spawn", classOf[SpawnHandler])
    addRequestHandler("collision", classOf[CollisionHandler])


    val user: User = null
    val room = getParentRoom
    trace("Initializing variables in " + room.getName)
    val roomVariables = spawnEverything()
    getApi.setRoomVariables(user, room, roomVariables, true, true, true)
  }


  private def spawnEverything(): List[RoomVariable] = {
    val roomVariables = new ListBuffer[RoomVariable]
    gameEngine.players foreach {
      mob =>
        trace("Creating variable for NPC: " + mob)
        new SFSRoomVariable(mob.uuid.toString, toSFSObject(mob))
      // TODO create variable
    }
    gameEngine.entities foreach {
      entity =>
        trace("Creating variable for item: " + entity)
        new SFSRoomVariable(entity.uuid.toString, toSFSObject(entity))
      // TODO create item

    }
    roomVariables.toList
  }

  private def toSFSObject(mob: Mob): ISFSObject = {
    val data = new SFSObject()
    data.putUtfString("id", mob.uuid.toString)
    data.putFloat("x", mob.x)
    data.putFloat("y", mob.y)
    data.putInt("s", mob.speed)
    data.putInt("d", mob.direction)
    data
  }

  private def toSFSObject(mob: Entity): ISFSObject = {
    val data = new SFSObject()
    data.putUtfString("id", mob.uuid.toString)
    data.putFloat("x", mob.x)
    data.putFloat("y", mob.y)
    data
  }

}
