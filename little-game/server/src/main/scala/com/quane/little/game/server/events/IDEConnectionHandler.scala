package com.quane.little.game.server.events

import com.smartfoxserver.v2.entities.User
import com.smartfoxserver.v2.entities.data.{SFSObject, ISFSObject}
import com.smartfoxserver.v2.mmo.Vec3D

class IDEConnectionHandler extends RequestHandler {

  override def handleClientRequest(user: User, data: ISFSObject): Unit = {
    trace("Authenticating IDE for '" + user + "' with " + data.getKeys + "..")
    val key = "ide_id"
    val value = data.get(key)
    value.getObject match {
      case "debug" => setDebugPosition(user)
      case _ => trace("AUTHENTICATING '" + value + "'")
    }
  }

  private def setDebugPosition(user: User) {
    trace("AUTHENTICATING DEBUGGER")
    val little = extension
    val room = little.getParentRoom
    val mmo = little.getMMOApi
    val position = new Vec3D(50.0f, 50.0f, 0)
    mmo.setUserPosition(user, position, room)
    val data = new SFSObject()
    data.putBool("valid", true)
    little.send(LittleEvents.IDE_AUTH, data, user)
  }

}
