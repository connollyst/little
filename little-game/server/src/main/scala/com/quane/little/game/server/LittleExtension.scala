package com.quane.little.game.server

import com.quane.little.game.server.events.{LittleEvents, IDEConnectionHandler, ServerReadyEventHandler, JoinEventHandler}

import com.smartfoxserver.v2.extensions.{ExtensionLogLevel, SFSExtension}
import com.smartfoxserver.v2.mmo._
import com.smartfoxserver.v2.SmartFoxServer

import collection.mutable.ListBuffer
import collection.JavaConversions._

/**
 * The SmartFox server 'extension' for the little game.
 *
 * @author Sean Connolly
 */
class LittleExtension
  extends SFSExtension
  with ClientCommunicator {

  val manager = new GameManager(this)
  val serializer = new ItemSerializer()

  override def init(): Unit = {
    trace("Initializing Little room..")
    if (!isMMORoom) {
      trace(ExtensionLogLevel.ERROR, "Not configured to be an MMO room!")
    }
    manager.init()
    initMMOItems()
    initHandlers()
  }

  def start(): Unit = {
    manager.start()
  }

  private def initMMOItems() =
    manager.game.entities.values foreach {
      mob =>
        val id = mob.uuid
        val data = serializer.serialize(mob)
        manager.addItem(id, new MMOItem(data))
    }

  private def initHandlers() = {
    addEventHandler(LittleEvents.USER_JOIN_ROOM, classOf[JoinEventHandler])
    addEventHandler(LittleEvents.SERVER_READY, new ServerReadyEventHandler())
    addRequestHandler(LittleEvents.IDE_AUTH, new IDEConnectionHandler())
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

  def isMMORoom: Boolean = getParentRoom.isInstanceOf[MMORoom]

  def getMMOApi = getServer.getAPIManager.getMMOApi

  def getServer = SmartFoxServer.getInstance()

}
