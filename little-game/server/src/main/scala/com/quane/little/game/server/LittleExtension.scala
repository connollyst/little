package com.quane.little.game.server

import com.quane.little.game.{TimedUpdater, LittleGameEngine}
import com.quane.little.game.server.events.JoinEventHandler

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
  val timer = new TimedUpdater(15) {
    def update() = sendItems()
  }

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
    addEventHandler(SFSEventType.USER_JOIN_ROOM, classOf[JoinEventHandler])
    game.start()
    new Thread(timer).start()
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
        val variables = new ListBuffer[IMMOItemVariable]
        variables += new MMOItemVariable("uuid", uuid.toString)
        variables += new MMOItemVariable("type", "player")
        variables += new MMOItemVariable("s", player.speed)
        variables += new MMOItemVariable("d", player.direction)
        val item = new MMOItem(variables.toList)
        // trace("Creating player MMO item: " + player)
        val position = new Vec3D(player.x, player.y, 0)
        getMMOApi.setMMOItemPosition(item, position, getParentRoom)
    }
  }

  private def initEntityMMOItems() = {
    game.entities.keys foreach {
      uuid =>
        val entity = game.entities(uuid)
        val variables = new ListBuffer[IMMOItemVariable]
        variables += new MMOItemVariable("uuid", uuid.toString)
        variables += new MMOItemVariable("type", "entity")
        val item = new MMOItem(variables.toList)
        // trace("Creating entity MMO item: " + entity)
        val position = new Vec3D(entity.x, entity.y, 0)
        getMMOApi.setMMOItemPosition(item, position, getParentRoom)
    }
  }

  private def initWallMMOItems() = {
    game.walls.keys foreach {
      uuid =>
        val wall = game.walls(uuid)
        val variables = new ListBuffer[IMMOItemVariable]
        variables += new MMOItemVariable("uuid", uuid.toString)
        variables += new MMOItemVariable("type", "wall")
        variables += new MMOItemVariable("w", wall.w.toInt)
        variables += new MMOItemVariable("h", wall.h.toInt)
        val item = new MMOItem(variables.toList)
        // trace("Creating wall MMO item: " + wall)
        val position = new Vec3D(wall.x, wall.y, 0)
        getMMOApi.setMMOItemPosition(item, position, getParentRoom)
    }
  }

  // TODO we can avoid newing up MMOItems
  // TODO maybe the game can give us notifications?
  private def sendItems() = {
    val api = getMMOApi
    val room = getParentRoom
    items.keys foreach {
      id =>
        val item = items(id)
        val entity = game.entity(id)
        val position = new Vec3D(entity.x, entity.y, 0)
        api.setMMOItemPosition(item, position, room)
    }
  }

  def isMMORoom: Boolean = getParentRoom.isInstanceOf[MMORoom]

  def getMMOApi = getServer.getAPIManager.getMMOApi

  def getServer = SmartFoxServer.getInstance()

}
