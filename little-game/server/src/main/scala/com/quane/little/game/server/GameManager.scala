package com.quane.little.game.server

import com.quane.little.game.entity.{EntityRemovalListener, Entity}
import com.quane.little.game.{TimedUpdater, Game}
import com.quane.little.tools.Logging
import com.smartfoxserver.v2.mmo.{Vec3D, MMOItem}
import scala.collection.mutable

/** Manages a game simulation running on the server.
  *
  * @param client the client communication manager
  * @param game the game simulation
  *
  * @author Sean Connolly
  */
class GameManager(client: ClientCommunicator, val game: Game)
  extends EntityRemovalListener
  with Logging {

  val items: mutable.Map[String, MMOItem] = mutable.Map()
  val updater = new TimedUpdater(15, sendItems)

  game.cleaner.add(this)

  def init() = {
    game.initialize()
    game.entities.values foreach {
      entity =>
        info("Adding game item " + entity)
        val id = entity.id
        val item = client.createItem(entity)
        items += (id -> item)
    }
  }

  def start() = {
    game.start()
    new Thread(updater).start()
  }

  def stop() = {
    updater.stop()
    game.stop()
  }

  def sendItems() =
    items.keys foreach {
      id =>
        val item = items(id)
        try {
          val entity = game.entity(id)
          val position = new Vec3D(entity.x, entity.y)
          client.setItemPosition(item, position)
        } catch {
          case e: Exception => error("Failed to send " + id + " to client.", e)
        }
    }

  override def entityRemoved(entity: Entity) =
    items.remove(entity.id) match {
      case Some(item) => client.removeItem(item)
      case _ => error("Tried to remove unknown MMO item " + entity)
    }

}
