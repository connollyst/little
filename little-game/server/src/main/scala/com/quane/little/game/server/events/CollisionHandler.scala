package com.quane.little.game.server.events

import com.smartfoxserver.v2.entities.User
import com.smartfoxserver.v2.entities.data.ISFSObject

class CollisionHandler extends LittleClientRequestHandler {

  override def handleClientRequest(user: User, data: ISFSObject): Unit = {
    val idA = data.getUtfString("a")
    val idB = data.getUtfString("b")
    getParentExtension.trace(
      "Player " + user.getPlayerId + " reported collision between " + idA + " & " + idB
    )
    getGameEngine.handleInteraction(idA, idB)
  }

}
