package com.quane.little.game.server

import com.quane.little.game.entity.Entity
import com.smartfoxserver.v2.core.SFSEventType
import com.smartfoxserver.v2.extensions.{IServerEventHandler, IClientRequestHandler}
import com.smartfoxserver.v2.mmo.{Vec3D, MMOItem}

trait ClientCommunicator {

  def createItem(entity: Entity): MMOItem

  def removeItem(item: MMOItem): Unit

  def setItemPosition(item: MMOItem, position: Vec3D)

  def addHandler(eventType: SFSEventType, handler: IServerEventHandler)

  def addHandler(requestId: String, requestHandler: IClientRequestHandler)

}
