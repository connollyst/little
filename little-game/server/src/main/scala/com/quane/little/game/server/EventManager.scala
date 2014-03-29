package com.quane.little.game.server

import com.quane.little.game.server.events.{IDEConnectionHandler, ServerReadyEventHandler, JoinEventHandler, LittleEvents}

/**
 *
 *
 * @author Sean Connolly
 */
class EventManager(client: ClientCommunicator) {

  def init(): Unit = {
    client.addListener(LittleEvents.USER_JOIN_ROOM, new JoinEventHandler)
    client.addListener(LittleEvents.SERVER_READY, new ServerReadyEventHandler)
    client.addListener(LittleEvents.IDE_AUTH, new IDEConnectionHandler)
  }

}
