package com.quane.little.game.server.events

import com.smartfoxserver.v2.core.ISFSEvent

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
    val position = extension.game.connect(username)
    getMMOApi.setUserPosition(user, position, room)
    trace(username + " spawned at " + position)
  }

}
