package com.quane.little.game.server

import com.smartfoxserver.v2.entities.User
import com.smartfoxserver.v2.entities.data.{SFSObject, ISFSObject}
import com.smartfoxserver.v2.exceptions.SFSRuntimeException
import com.quane.little.game.entity.Mob

/** Handler for client requests to spawn a mob.
  *
  * @author Sean Connolly
  */
class SpawnHandler extends LittleClientRequestHandler {

  /** Handle the client's request to spawn a new mob.
    *
    * @param user the user client
    * @param params the request parameters
    */
  override def handleClientRequest(user: User, params: ISFSObject): Unit = {
    checkParams(params)
    val x = params.getFloat("x")
    val y = params.getFloat("y")
    getParentExtension.trace(
      "Handling spawn for player " + user.getPlayerId + " at (" + x + ", " + y + ").."
    )
    spawnMob(x, y)
  }

  private def checkParams(params: ISFSObject) = {
    if (!params.containsKey("x") || !params.containsKey("y")) {
      throw new SFSRuntimeException("Invalid request, expected 'x' & 'y'")
    }
  }

  private def spawnMob(x: Float, y: Float) = {
    val mob = getGameEngine.entityFactory.createMob
    mob.x = x
    mob.y = y
    notifyAll(mob)
  }

  private def notifyAll(mob: Mob) = {
    val response = new SFSObject()
    response.putFloat("x", mob.x)
    response.putFloat("y", mob.y)
    response.putUtfString("id", mob.uuid.toString)
    send("spawn", response, getLittleExtension.getParentRoom.getUserList)
  }

}
