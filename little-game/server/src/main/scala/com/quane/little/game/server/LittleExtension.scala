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

  override def init(): Unit = {
    trace("Initializing Little room..")
    if (!isMMORoom) {
      trace(ExtensionLogLevel.ERROR, "Not configured to be an MMO room!")
    }
    manager.init()
    initMMOItems()
    addEventHandler(LittleEvents.USER_JOIN_ROOM, classOf[JoinEventHandler])
    addEventHandler(LittleEvents.SERVER_READY, new ServerReadyEventHandler())
    addRequestHandler(LittleEvents.IDE_AUTH, new IDEConnectionHandler())
  }

  def start(): Unit = {
    manager.start()
  }

  private def initMMOItems() = {
    initPlayerMMOItems()
    initEntityMMOItems()
    initWallMMOItems()
  }

  private def initPlayerMMOItems() = {
    manager.game.players.keys foreach {
      uuid =>
        val player = manager.game.players(uuid)
        trace("Creating player MMO item: " + player)
        val variables = new ListBuffer[IMMOItemVariable]
        variables += new MMOItemVariable("uuid", uuid)
        variables += new MMOItemVariable("type", "player")
        variables += new MMOItemVariable("s", player.speed)
        variables += new MMOItemVariable("d", player.direction)
        createItem(uuid, variables.toList)
    }
  }

  private def initEntityMMOItems() = {
    manager.game.entities.keys foreach {
      uuid =>
        val entity = manager.game.entities(uuid)
        trace("Creating entity MMO item: " + entity)
        val variables = new ListBuffer[IMMOItemVariable]
        variables += new MMOItemVariable("uuid", uuid)
        variables += new MMOItemVariable("type", "entity")
        createItem(uuid, variables.toList)
    }
  }

  private def initWallMMOItems() = {
    manager.game.walls.keys foreach {
      uuid =>
        val wall = manager.game.walls(uuid)
        trace("Creating wall MMO item: " + wall)
        val variables = new ListBuffer[IMMOItemVariable]
        variables += new MMOItemVariable("uuid", uuid)
        variables += new MMOItemVariable("type", "wall")
        variables += new MMOItemVariable("w", wall.w.toInt)
        variables += new MMOItemVariable("h", wall.h.toInt)
        createItem(uuid, variables.toList)
    }
  }

  def createItem(uuid: String, variables: List[IMMOItemVariable]) =
    manager.addItem(uuid, new MMOItem(variables))

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
