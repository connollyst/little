package com.quane.little.game.server.events

import com.smartfoxserver.v2.entities.User
import com.smartfoxserver.v2.entities.data.{SFSObject, ISFSObject}
import com.smartfoxserver.v2.exceptions.SFSRuntimeException
import com.quane.little.game.entity.Mob

/**
 *
 *
 * @author Sean Connolly
 */
class MoveHandler
  extends LittleClientRequestHandler {

  override def handleClientRequest(user: User, params: ISFSObject): Unit = {
    checkParams(params)
    val id = params.getUtfString("id")
    val x = params.getFloat("x")
    val y = params.getFloat("y")
    getParentExtension.trace(
      "Handling move from player " + user.getPlayerId + " to (" + x + ", " + y + ").."
    )
    moveMob(id, x, y)
  }

  private def checkParams(params: ISFSObject) = {
    if (!params.containsKey("id") || !params.containsKey("x") || !params.containsKey("y")) {
      throw new SFSRuntimeException("Invalid request, expected 'id', 'x', & 'y'")
    }
  }

  private def moveMob(id: String, x: Float, y: Float) = {
    val mob = getGameEngine.setEntityPosition(id, x, y)
    notifyAll(mob)
  }

  private def notifyAll(mob: Mob) = {
    val response = new SFSObject()
    response.putFloat("x", mob.x)
    response.putFloat("y", mob.y)
    response.putUtfString("id", mob.uuid.toString)
    send("move", response, getLittleExtension.getParentRoom.getUserList)
  }

}
