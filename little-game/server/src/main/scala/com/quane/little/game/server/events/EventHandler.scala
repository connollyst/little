package com.quane.little.game.server.events

import com.quane.little.game.server.LittleExtension
import com.smartfoxserver.v2.api.ISFSMMOApi
import com.smartfoxserver.v2.core.{SFSEventParam, ISFSEvent}
import com.smartfoxserver.v2.entities.{User, Room}
import com.smartfoxserver.v2.extensions.BaseServerEventHandler
import com.smartfoxserver.v2.mmo.MMORoom

/**
 *
 *
 * @author Sean Connolly
 */
abstract class EventHandler
  extends BaseServerEventHandler {

  protected def extension: LittleExtension =
    getParentExtension match {
      case little: LittleExtension => little
      case _ => throw new IllegalAccessException("Expected LittleExtension.")
    }

  protected def getRoom(event: ISFSEvent): MMORoom = {
    event.getParameter(SFSEventParam.ROOM) match {
      case room: MMORoom => room
      case room: Room =>
        throw new IllegalAccessException("Expected MMO room, found " + room)
      case _ =>
        throw new IllegalAccessException("Expected ROOM parameter: " + event)
    }
  }

  protected def getUser(event: ISFSEvent): User = {
    event.getParameter(SFSEventParam.USER) match {
      case user: User => user
      case _ =>
        throw new IllegalAccessException("Expected USER parameter: " + event)
    }
  }

  protected def getMMOApi: ISFSMMOApi = extension.getMMOApi

}
