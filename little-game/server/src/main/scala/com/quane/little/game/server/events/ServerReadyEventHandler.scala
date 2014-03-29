package com.quane.little.game.server.events

import com.smartfoxserver.v2.core.ISFSEvent

/** Handler for when the server initially becomes 'ready'. Starts the game.
  *
  * @author Sean Connolly
  */
class ServerReadyEventHandler
  extends EventHandler {

  override def handleServerEvent(event: ISFSEvent) = extension.game.start()

}
