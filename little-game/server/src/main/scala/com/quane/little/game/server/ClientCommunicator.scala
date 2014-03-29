package com.quane.little.game.server

import com.smartfoxserver.v2.mmo.{Vec3D, MMOItem}
import com.smartfoxserver.v2.extensions.{IServerEventHandler, IClientRequestHandler}
import com.smartfoxserver.v2.core.SFSEventType

trait ClientCommunicator {

  def removeItem(item: MMOItem): Unit

  def setItemPosition(item: MMOItem, position: Vec3D)

  def addListener(eventType: SFSEventType, handler: IServerEventHandler)

  def addListener(requestId: String, requestHandler: IClientRequestHandler)

}
