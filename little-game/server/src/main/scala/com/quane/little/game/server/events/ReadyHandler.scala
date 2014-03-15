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
    sendState(user)
  }


}
