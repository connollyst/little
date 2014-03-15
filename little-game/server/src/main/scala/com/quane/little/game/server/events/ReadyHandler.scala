package com.quane.little.game.server.events

import com.smartfoxserver.v2.entities.User
import com.smartfoxserver.v2.entities.data.ISFSObject
import com.smartfoxserver.v2.entities.variables.{SFSUserVariable, UserVariable}
import scala.collection.mutable.ListBuffer
import scala.collection.JavaConversions._

/** Handler for client request indicating they are ready to begin the game.
  *
  * @author Sean Connolly
  */
class ReadyHandler
  extends LittleClientRequestHandler {

  /** Handle the client's request indicating they are ready to begin the game.
    *
    * @param user the user client
    * @param params the request parameters
    */
  override def handleClientRequest(user: User, params: ISFSObject): Unit = {
    getParentExtension.trace(
      "Player " + user.getPlayerId + " is ready to play, sending state.."
    )
    //    sendState(user)
    getApi.setUserVariables(user, getUserVariables(user))
    getLittleExtension.setRoomVariables()
  }

  private def getUserVariables(user: User): List[UserVariable] = {
    val userVariables = new ListBuffer[UserVariable]
    getGameEngine.players.keys foreach {
      uuid =>
        getParentExtension.trace("Setting " + uuid + " for " + user.getPlayerId)
        val player = getGameEngine.players(uuid)
        val data = toSFSObject(player)
        userVariables += new SFSUserVariable(uuid, data)
    }
    userVariables.toList
  }

}
