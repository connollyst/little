package com.quane.little.game.server

import com.smartfoxserver.v2.mmo.{Vec3D, MMOItem}

trait ClientCommunicator {

  def removeItem(item: MMOItem): Unit

  def setItemPosition(item: MMOItem, position: Vec3D)

}
