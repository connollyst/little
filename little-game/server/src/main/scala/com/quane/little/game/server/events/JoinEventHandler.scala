package com.quane.little.game.server.events

import com.smartfoxserver.v2.core.ISFSEvent
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
    val position = new Vec3D(50.0f, 50.0f, 0)
    getMMOApi.setUserPosition(user, position, room)
  }

}
