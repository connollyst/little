package com.quane.little.game.server

import com.quane.little.game.server.events.{IDEConnectionHandler, ServerReadyEventHandler, JoinEventHandler, LittleEvents}

/** Manages [[ClientCommunicator]] event handlers.
  *
  * @author Sean Connolly
  */
class EventManager(client: ClientCommunicator) {

  def init(): Unit = {
    client.addHandler(LittleEvents.USER_JOIN_ROOM, new JoinEventHandler)
    client.addHandler(LittleEvents.SERVER_READY, new ServerReadyEventHandler)
    client.addHandler(LittleEvents.IDE_AUTH, new IDEConnectionHandler)
  }

}
