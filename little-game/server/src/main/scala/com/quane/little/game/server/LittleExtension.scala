package com.quane.little.game.server

import com.quane.little.game.{TimedUpdater, LittleGameEngine}
import com.quane.little.game.server.events.{LittleEvents, IDEConnectionHandler, ServerReadyEventHandler, JoinEventHandler}

import com.smartfoxserver.v2.extensions.{ExtensionLogLevel, SFSExtension}
import com.smartfoxserver.v2.mmo._
import com.smartfoxserver.v2.core.SFSEventType
import com.smartfoxserver.v2.SmartFoxServer

import collection.mutable
import collection.mutable.ListBuffer
import collection.JavaConversions._

/**
 * The SmartFox server 'extension' for the little game.
 *
 * @author Sean Connolly
 */
class LittleExtension
  extends SFSExtension {

  val items: mutable.Map[String, MMOItem] = mutable.Map()
  val game = new LittleGameEngine
  val updater = new Thread(new TimedUpdater(15) {
    def update() = sendItems()
  })

  override def init(): Unit = {
    trace("Initializing Little room..")
    if (!isMMORoom) {
      trace(ExtensionLogLevel.ERROR, "Not configured to be an MMO room!")
    }
    game.initialize()
    initMMOItems()
    trace("Initialized with "
      + game.players.size + " mobs, "
      + game.entities.size + " items, & "
      + game.walls.size + " walls.."
    )
    addEventHandler(LittleEvents.USER_JOIN_ROOM, classOf[JoinEventHandler])
    addEventHandler(LittleEvents.SERVER_READY, new ServerReadyEventHandler())
    addRequestHandler(LittleEvents.IDE_AUTH, new IDEConnectionHandler())
  }

  def start(): Unit = {
    game.start()
    updater.start()
  }

  private def initMMOItems() = {
    initPlayerMMOItems()
    initEntityMMOItems()
    initWallMMOItems()
  }

  private def initPlayerMMOItems() = {
    game.players.keys foreach {
      uuid =>
        val player = game.players(uuid)
        trace("Creating player MMO item: " + player)
        val variables = new ListBuffer[IMMOItemVariable]
        variables += new MMOItemVariable("uuid", uuid)
        variables += new MMOItemVariable("type", "player")
        variables += new MMOItemVariable("s", player.speed)
        variables += new MMOItemVariable("d", player.direction)
        createMMOItem(uuid, variables.toList)
    }
  }

  private def initEntityMMOItems() = {
    game.entities.keys foreach {
      uuid =>
        val entity = game.entities(uuid)
        trace("Creating entity MMO item: " + entity)
        val variables = new ListBuffer[IMMOItemVariable]
        variables += new MMOItemVariable("uuid", uuid)
        variables += new MMOItemVariable("type", "entity")
        createMMOItem(uuid, variables.toList)
    }
  }

  private def initWallMMOItems() = {
    game.walls.keys foreach {
      uuid =>
        val wall = game.walls(uuid)
        trace("Creating wall MMO item: " + wall)
        val variables = new ListBuffer[IMMOItemVariable]
        variables += new MMOItemVariable("uuid", uuid)
        variables += new MMOItemVariable("type", "wall")
        variables += new MMOItemVariable("w", wall.w.toInt)
        variables += new MMOItemVariable("h", wall.h.toInt)
        createMMOItem(uuid, variables.toList)
    }
  }

  private def createMMOItem(uuid: String, variables: List[IMMOItemVariable]) = {
    items += (uuid -> new MMOItem(variables))
  }

  // TODO maybe the game can give us notifications?
  private def sendItems() = {
    val api = getMMOApi
    val room = getParentRoom
    items.keys foreach {
      id =>
        val item = items(id)
        try {
          val entity = game.entity(id)
          val x = entity.x
          val y = entity.y
          val position = new Vec3D(x, y, 0)
          val vars = new ListBuffer[IMMOItemVariable]
          vars += new MMOItemVariable("x", x)
          vars += new MMOItemVariable("y", y)
          api.setMMOItemPosition(item, position, room)
          api.setMMOItemVariables(item, vars.toList)
        } catch {
          case e: Exception => trace(ExtensionLogLevel.ERROR, e)
        }
    }
  }

  def isMMORoom: Boolean = getParentRoom.isInstanceOf[MMORoom]

  def getMMOApi = getServer.getAPIManager.getMMOApi

  def getServer = SmartFoxServer.getInstance()

}
