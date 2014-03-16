package com.quane.little.game.server

import com.smartfoxserver.v2.extensions.{ExtensionLogLevel, SFSExtension}
import com.quane.little.game.{TimedUpdater, LittleGameEngine}
import com.smartfoxserver.v2.mmo._
import com.smartfoxserver.v2.core.SFSEventType
import com.quane.little.game.server.events.JoinEventHandler
import com.smartfoxserver.v2.SmartFoxServer
import scala.collection.mutable.ListBuffer
import scala.collection.JavaConversions._

/**
 * The SmartFox server 'extension' for the little game.
 *
 * @author Sean Connolly
 */
class LittleExtension
  extends SFSExtension {

  var gameEngine: LittleGameEngine = null
  val updater = new TimedUpdater(2) {
    def update() = sendItems()
  }

  override def init(): Unit = {
    trace("Initializing Little room..")
    if (!isMMORoom) {
      trace(ExtensionLogLevel.ERROR, "Not configured to be an MMO room!")
    }
    gameEngine = new LittleGameEngine
    gameEngine.initialize()
    trace("Initialized with "
      + gameEngine.players.size + " mobs, "
      + gameEngine.entities.size + " items, & "
      + gameEngine.walls.size + " walls.."
    )
    gameEngine.start()
    new Thread(updater).start()
    addEventHandler(SFSEventType.USER_JOIN_ROOM, classOf[JoinEventHandler])
  }

  private def initMMOItems() = {
    initPlayerMMOItems()
    initEntityMMOItems()
    initWallMMOItems()
  }

  private def initPlayerMMOItems() = {
    gameEngine.players.keys foreach {
      uuid =>
        val player = gameEngine.players(uuid)
        val variables = new ListBuffer[IMMOItemVariable]
        variables += new MMOItemVariable("uuid", uuid.toString)
        variables += new MMOItemVariable("type", "player")
        variables += new MMOItemVariable("s", player.speed)
        variables += new MMOItemVariable("d", player.direction)
        val item = new MMOItem(variables.toList)
        // trace("Creating player MMO item: " + player)
        val position = new Vec3D(player.x.toInt, player.y.toInt, 0)
        getMMOApi.setMMOItemPosition(item, position, getParentRoom)
    }
  }

  private def initEntityMMOItems() = {
    gameEngine.entities.keys foreach {
      uuid =>
        val entity = gameEngine.entities(uuid)
        val variables = new ListBuffer[IMMOItemVariable]
        variables += new MMOItemVariable("uuid", uuid.toString)
        variables += new MMOItemVariable("type", "entity")
        val item = new MMOItem(variables.toList)
        // trace("Creating entity MMO item: " + entity)
        val position = new Vec3D(entity.x.toInt, entity.y.toInt, 0)
        getMMOApi.setMMOItemPosition(item, position, getParentRoom)
    }
  }

  private def initWallMMOItems() = {
    gameEngine.walls.keys foreach {
      uuid =>
        val wall = gameEngine.walls(uuid)
        val variables = new ListBuffer[IMMOItemVariable]
        variables += new MMOItemVariable("uuid", uuid.toString)
        variables += new MMOItemVariable("type", "wall")
        variables += new MMOItemVariable("w", wall.w.toInt)
        variables += new MMOItemVariable("h", wall.h.toInt)
        val item = new MMOItem(variables.toList)
        // trace("Creating wall MMO item: " + wall)
        val position = new Vec3D(wall.x.toInt, wall.y.toInt, 0)
        getMMOApi.setMMOItemPosition(item, position, getParentRoom)
    }
  }

  // TODO we can avoid newing up MMOItems
  // TODO maybe the game can give us notifications?
  private def sendItems() = initMMOItems()

  def isMMORoom: Boolean = getParentRoom.isInstanceOf[MMORoom]

  def getMMOApi = getServer.getAPIManager.getMMOApi

  def getServer = SmartFoxServer.getInstance()

}
