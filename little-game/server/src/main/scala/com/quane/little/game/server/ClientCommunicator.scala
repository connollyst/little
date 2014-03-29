package com.quane.little.game.server

import com.smartfoxserver.v2.mmo.{Vec3D, MMOItem}
import com.smartfoxserver.v2.extensions.{IServerEventHandler, IClientRequestHandler}
import com.smartfoxserver.v2.core.SFSEventType
import com.quane.little.game.entity.Entity

trait ClientCommunicator {

  def createItem(entity: Entity): MMOItem

  def removeItem(item: MMOItem): Unit

  def setItemPosition(item: MMOItem, position: Vec3D)

  def addListener(eventType: SFSEventType, handler: IServerEventHandler)

  def addListener(requestId: String, requestHandler: IClientRequestHandler)

}
