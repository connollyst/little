package com.quane.little.game.server.events

import com.smartfoxserver.v2.extensions.BaseServerEventHandler
import com.smartfoxserver.v2.core.{SFSEventParam, ISFSEvent}
import com.smartfoxserver.v2.entities.{Room, User}
import com.smartfoxserver.v2.api.ISFSMMOApi
import com.smartfoxserver.v2.mmo.{MMORoom, Vec3D}
import com.quane.little.game.server.LittleExtension

/** Handler for when a user joins the room.
  *
  * @author Sean Connolly
  */
class JoinEventHandler
  extends BaseServerEventHandler {

  override def handleServerEvent(event: ISFSEvent): Unit = {
    val room = getRoom(event)
    val user = getUser(event)
    initializeUser(user, room)
  }

  private def getRoom(event: ISFSEvent): MMORoom = {
    event.getParameter(SFSEventParam.ROOM) match {
      case room: MMORoom => room
      case room: Room =>
        throw new IllegalAccessException("Expected MMO room, found " + room)
      case _ =>
        throw new IllegalAccessException("Expected ROOM parameter: " + event)
    }
  }

  private def getUser(event: ISFSEvent): User = {
    event.getParameter(SFSEventParam.USER) match {
      case user: User => user
      case _ =>
        throw new IllegalAccessException("Expected USER parameter: " + event)
    }
  }

  private def getMMOApi: ISFSMMOApi = {
    getParentExtension.asInstanceOf[LittleExtension].getMMOApi
  }

  private def initializeUser(user: User, room: Room) {
    trace("User joined: " + user)
    getMMOApi.setUserPosition(user, new Vec3D(0, 0, 0), room)
  }

}
