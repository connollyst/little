package com.quane.little.game.server

import collection.JavaConversions._
import com.quane.little.game.entity.Entity
import com.smartfoxserver.v2.SmartFoxServer
import com.smartfoxserver.v2.core.SFSEventType
import com.smartfoxserver.v2.extensions.{IClientRequestHandler, IServerEventHandler, ExtensionLogLevel, SFSExtension}
import com.smartfoxserver.v2.mmo._
import com.quane.little.game.Game
import com.quane.little.data.DataBindingModule

/**
 * The SmartFox server 'extension' for the little game.
 *
 * @author Sean Connolly
 */
class LittleExtension
  extends SFSExtension
  with ClientCommunicator {

  implicit val bindingModule = DataBindingModule

  val game = new GameManager(this, new Game)
  val events = new EventManager(this)
  val serializer = new EntitySerializer()

  override def init(): Unit = {
    if (!isMMORoom) {
      trace(ExtensionLogLevel.ERROR, "Must be configured as an MMO room.")
    }
    game.init()
    events.init()
  }

  override def createItem(entity: Entity): MMOItem =
    new MMOItem(serializer.serialize(entity))

  override def removeItem(item: MMOItem) =
  // TODO this doesn't seem to remove the item from already connected clients
    getMMOApi.removeMMOItem(item)

  override def setItemPosition(item: MMOItem, position: Vec3D) = {
    val api = getMMOApi
    val data = serializer.serialize(position)
    api.setMMOItemPosition(item, position, room) // must come first to ensure item is added to room
    api.setMMOItemVariables(item, data)
  }

  override def addHandler(requestId: String, requestHandler: IClientRequestHandler) =
    addRequestHandler(requestId, requestHandler)

  override def addHandler(eventType: SFSEventType, handler: IServerEventHandler) =
    addEventHandler(eventType, handler)

  def getMMOApi = server.getAPIManager.getMMOApi

  def server = SmartFoxServer.getInstance()

  def room: MMORoom = getParentRoom.asInstanceOf[MMORoom]

  private def isMMORoom: Boolean = getParentRoom.isInstanceOf[MMORoom]

}
