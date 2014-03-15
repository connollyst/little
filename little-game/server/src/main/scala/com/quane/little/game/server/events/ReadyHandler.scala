package com.quane.little.game.server.events

import com.smartfoxserver.v2.entities.User
import com.smartfoxserver.v2.entities.data.{SFSObject, ISFSObject}
import com.quane.little.game.entity.{Entity, Mob}

/** Handler for client request indicating they are ready to begin the game.
  *
  * @author Sean Connolly
  */
class ReadyHandler extends LittleClientRequestHandler {

  /** Handle the client's request indicating they are ready to begin the game.
    *
    * @param user the user client
    * @param params the request parameters
    */
  override def handleClientRequest(user: User, params: ISFSObject): Unit = {
    getParentExtension.trace(
      "Player " + user.getPlayerId + " is ready to play, sending state.."
    )
    spawnEverything(user)
  }

  private def spawnEverything(user: User) = {
    getGameEngine.players foreach {
      mob =>
        getParentExtension.trace("Sending " + mob + " to " + user.getPlayerId)
        val data = toSFSObject(mob)
        send("addPlayer", data, user)
    }
    getGameEngine.entities foreach {
      entity =>
        getParentExtension.trace("Sending " + entity + " to " + user.getPlayerId)
        val data = toSFSObject(entity)
        send("addItem", data, user)
    }
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
