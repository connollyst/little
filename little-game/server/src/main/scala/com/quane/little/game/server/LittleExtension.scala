package com.quane.little.game.server


import com.smartfoxserver.v2.extensions.{IClientRequestHandler, IServerEventHandler, ExtensionLogLevel, SFSExtension}
import com.smartfoxserver.v2.mmo._
import com.smartfoxserver.v2.SmartFoxServer

import collection.JavaConversions._
import com.smartfoxserver.v2.core.SFSEventType

/**
 * The SmartFox server 'extension' for the little game.
 *
 * @author Sean Connolly
 */
class LittleExtension
  extends SFSExtension
  with ClientCommunicator {

  val game = new GameManager(this)
  val events = new EventManager(this)
  val serializer = new ItemSerializer()

  override def init(): Unit = {
    trace("Initializing Little room..")
    if (!isMMORoom) {
      trace(ExtensionLogLevel.ERROR, "Not configured to be an MMO room!")
    }
    game.init()
    events.init()
    initMMOItems()
  }

  def start(): Unit = {
    game.start()
  }

  private def initMMOItems() =
    game.game.entities.values foreach {
      entity =>
        val id = entity.uuid
        val data = serializer.serialize(entity)
        game.addItem(id, new MMOItem(data))
    }

  override def removeItem(item: MMOItem) =
    getMMOApi.removeMMOItem(item)

  override def setItemPosition(item: MMOItem, position: Vec3D) = {
    val api = getMMOApi
    val data = serializer.serialize(position)
    api.setMMOItemVariables(item, data)
    api.setMMOItemPosition(item, position, room)
  }

  override def addListener(requestId: String, requestHandler: IClientRequestHandler) =
    addRequestHandler(requestId, requestHandler)

  override def addListener(eventType: SFSEventType, handler: IServerEventHandler) =
    addEventHandler(eventType, handler)

  def getMMOApi = server.getAPIManager.getMMOApi

  def server = SmartFoxServer.getInstance()

  def room: MMORoom = getParentRoom.asInstanceOf[MMORoom]

  private def isMMORoom: Boolean = getParentRoom.isInstanceOf[MMORoom]

}
