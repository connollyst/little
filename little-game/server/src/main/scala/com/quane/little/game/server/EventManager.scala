package com.quane.little.game.server

import com.quane.little.game.server.events.{ServerReadyEventHandler, JoinEventHandler, LittleEvents}
import com.escalatesoft.subcut.inject.BindingModule

/** Manages [[com.quane.little.game.server.ClientCommunicator]] event handlers.
  *
  * @author Sean Connolly
  */
class EventManager(client: ClientCommunicator)(implicit val bindingModule: BindingModule) {

  def init(): Unit = {
    client.addHandler(LittleEvents.USER_JOIN_ROOM, new JoinEventHandler)
    client.addHandler(LittleEvents.SERVER_READY, new ServerReadyEventHandler)
  }

}
