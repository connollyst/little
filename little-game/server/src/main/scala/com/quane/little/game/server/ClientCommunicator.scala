package com.quane.little.game.server

import com.quane.little.game.entity.Entity
import com.smartfoxserver.v2.core.SFSEventType
import com.smartfoxserver.v2.extensions.{IServerEventHandler, IClientRequestHandler}
import com.smartfoxserver.v2.mmo.{Vec3D, MMOItem}

/** Manages communication with the client, which may be one or more end users.
  *
  * @author Sean Connolly
  */
trait ClientCommunicator {

  /** Create the [[com.smartfoxserver.v2.mmo.MMOItem]] representation of the
    * given [[com.quane.little.game.entity.Entity]].
    *
    * @param entity the Entity to create as an MMO item
    * @return the MMO item representation of the Entity
    */
  def createItem(entity: Entity): MMOItem

  /** Remove the [[com.smartfoxserver.v2.mmo.MMOItem]] from the client.
    *
    * @param item the item to remove
    */
  def removeItem(item: MMOItem): Unit

  /** Set the [[com.smartfoxserver.v2.mmo.MMOItem]] position, informing the
    * client of the change.
    *
    * @param item the item whose position to update
    * @param position the new item position
    */
  def setItemPosition(item: MMOItem, position: Vec3D)

  /** Add a handler for the specified type of event.
    *
    * @param eventType the event type to handle
    * @param handler the event handler
    */
  def addHandler(eventType: SFSEventType, handler: IServerEventHandler)

  /** Add a handler for the specified request id.
    *
    * @param requestId the request id to handle
    * @param requestHandler the request handler
    */
  def addHandler(requestId: String, requestHandler: IClientRequestHandler)

}
