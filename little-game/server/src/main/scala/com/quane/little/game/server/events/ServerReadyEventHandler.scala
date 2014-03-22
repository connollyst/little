package com.quane.little.game.server.events

import com.smartfoxserver.v2.extensions.BaseServerEventHandler
import com.quane.little.game.server.LittleExtension
import com.smartfoxserver.v2.core.ISFSEvent

/**
 *
 *
 * @author Sean Connolly
 */
class ServerReadyEventHandler
  extends BaseServerEventHandler {


  override def handleServerEvent(p1: ISFSEvent): Unit = {
    getLittleExtension.start()
  }

  private def getLittleExtension: LittleExtension = getParentExtension.asInstanceOf[LittleExtension]

}
