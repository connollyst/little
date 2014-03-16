package com.quane.little.game.server

import com.smartfoxserver.v2.extensions.SFSExtension
import com.quane.little.game.LittleGameEngine
import com.quane.little.game.server.events._
import com.smartfoxserver.v2.entities.variables.{SFSRoomVariable, RoomVariable}
import com.smartfoxserver.v2.entities.User
import com.quane.little.game.entity.Entity
import com.smartfoxserver.v2.entities.data.{SFSObject, ISFSObject}
import scala.collection.mutable.ListBuffer
import scala.collection.JavaConversions._
import com.smartfoxserver.v2.core.SFSEventType

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
    addRequestHandler("ready", classOf[ReadyHandler])
    addEventHandler(SFSEventType.USER_JOIN_ROOM, classOf[JoinEventHandler])
  }

  def setRoomVariables(): Unit = {
    trace("Setting room variables..")
    val user: User = null // the server
    val room = getParentRoom
    getApi.setRoomVariables(user, room, roomVariables)
  }

  private def roomVariables: List[RoomVariable] = {
    val roomVariables = new ListBuffer[RoomVariable]
    gameEngine.entities.keys foreach {
      uuid =>
        val entity = gameEngine.entities(uuid)
        trace("Creating room variable for item: " + entity)
        new SFSRoomVariable(uuid, toSFSObject(entity))
    }
    roomVariables.toList
  }

  private def toSFSObject(mob: Entity): ISFSObject = {
    val data = new SFSObject()
    data.putUtfString("id", mob.uuid.toString)
    data.putFloat("x", mob.x)
    data.putFloat("y", mob.y)
    data
  }

}
