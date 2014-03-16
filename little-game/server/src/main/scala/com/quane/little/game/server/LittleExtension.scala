package com.quane.little.game.server

import com.smartfoxserver.v2.extensions.{ExtensionLogLevel, SFSExtension}
import com.quane.little.game.LittleGameEngine
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

  override def init(): Unit = {
    trace("Initializing Little room..")
    if (!isMMORoom) {
      trace(ExtensionLogLevel.ERROR, "Not configured to be an MMO room!")
    }
    gameEngine = new LittleGameEngine
    gameEngine.initialize()
    trace("Initialized with "
      + gameEngine.entities.size + " items and "
      + gameEngine.players.size + " mobs."
    )
    addEventHandler(SFSEventType.USER_JOIN_ROOM, classOf[JoinEventHandler])
    gameEngine.entities.keys foreach {
      uuid =>
        val entity = gameEngine.entities(uuid)
        val variables = new ListBuffer[IMMOItemVariable]
        variables += new MMOItemVariable("uuid", uuid.toString)
        val item = new MMOItem(variables.toList)
        trace("Creating MMO item: " + entity)
        val position = new Vec3D(entity.x.toInt, entity.y.toInt, 0)
        getMMOApi.setMMOItemPosition(item, position, getParentRoom)
    }
  }

  private def isMMORoom: Boolean = getParentRoom.isInstanceOf[MMORoom]

  def getMMOApi = getServer.getAPIManager.getMMOApi

  def getServer = SmartFoxServer.getInstance()

}
