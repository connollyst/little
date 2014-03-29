package com.quane.little.game.server


import com.smartfoxserver.v2.extensions.{IClientRequestHandler, IServerEventHandler, ExtensionLogLevel, SFSExtension}
import com.smartfoxserver.v2.mmo._
import com.smartfoxserver.v2.SmartFoxServer

import collection.mutable.ListBuffer
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
      mob =>
        val id = mob.uuid
        val data = serializer.serialize(mob)
        game.addItem(id, new MMOItem(data))
    }

  override def removeItem(item: MMOItem) =
    getMMOApi.removeMMOItem(item)

  override def setItemPosition(item: MMOItem, position: Vec3D) = {
    val api = getMMOApi
    val room = getParentRoom
    val vars = new ListBuffer[IMMOItemVariable]
    vars += new MMOItemVariable("x", position.floatX())
    vars += new MMOItemVariable("y", position.floatY())
    api.setMMOItemPosition(item, position, room)
    api.setMMOItemVariables(item, vars.toList)
  }

  override def addListener(requestId: String, requestHandler: IClientRequestHandler) =
    addRequestHandler(requestId, requestHandler)

  override def addListener(eventType: SFSEventType, handler: IServerEventHandler) =
    addEventHandler(eventType, handler)

  def isMMORoom: Boolean = getParentRoom.isInstanceOf[MMORoom]

  def getMMOApi = getServer.getAPIManager.getMMOApi

  def getServer = SmartFoxServer.getInstance()

}
