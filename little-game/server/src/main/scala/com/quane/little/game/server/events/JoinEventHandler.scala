package com.quane.little.game.server.events

import com.smartfoxserver.v2.core.ISFSEvent
import com.smartfoxserver.v2.entities.{Room, User}
import com.smartfoxserver.v2.entities.data.SFSObject
import com.quane.little.game.entity.Entity
import com.smartfoxserver.v2.mmo.Vec3D

/** Handler for when a user joins the room.
  *
  * @author Sean Connolly
  */
class JoinEventHandler
  extends EventHandler {

  override def handleServerEvent(event: ISFSEvent): Unit = {
    val room = getRoom(event)
    val user = getUser(event)
    val username = user.getName
    trace(username + " signed in, connecting to game..")
    val player = extension.game.connect(username)
    setUserPosition(room, user, player)
    setPlayerId(user, player)
  }

  private def setUserPosition(room: Room, user: User, player: Entity): Unit = {
    val position = new Vec3D(player.x, player.y)
    getMMOApi.setUserPosition(user, position, room)
    trace(user.getName + " spawned at " + position)
  }

  private def setPlayerId(user: User, player: Entity): Unit = {
    val data = new SFSObject()
    data.putUtfString("mobId", player.id)
    extension.send(LittleEvents.DO_FOLLOW, data, user)
  }

}
