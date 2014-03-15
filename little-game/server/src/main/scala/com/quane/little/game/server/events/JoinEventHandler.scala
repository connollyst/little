package com.quane.little.game.server.events

import com.smartfoxserver.v2.extensions.BaseServerEventHandler
import com.smartfoxserver.v2.core.ISFSEvent
import com.quane.little.game.server.LittleExtension

class JoinEventHandler extends BaseServerEventHandler {

  override def handleServerEvent(event: ISFSEvent): Unit = {
    trace("User joined: " + event)
    getLittleExtension.setRoomVariables()
  }

  def getLittleExtension: LittleExtension =
    getParentExtension match {
      case little: LittleExtension => little
      case _ => throw new IllegalAccessException(
        "Expected parent extension to be LittleExtension."
      )
    }

}
