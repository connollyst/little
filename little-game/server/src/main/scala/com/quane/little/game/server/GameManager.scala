package com.quane.little.game.server

import com.quane.little.Logging
import com.quane.little.game.entity.{EntityRemovalListener, Entity}
import com.quane.little.game.{TimedUpdater, Game}
import com.smartfoxserver.v2.mmo.{Vec3D, MMOItem}
import scala.collection.mutable

class GameManager(client: ClientCommunicator,
                  val game: Game = new Game)
  extends EntityRemovalListener
  with Logging {

  val items: mutable.Map[String, MMOItem] = mutable.Map()
  val updater = new Thread(new TimedUpdater(15) {
    def update() = sendItems()
  })

  def init() = {
    game.initialize()
    game.entities.values foreach {
      entity =>
        println("Adding game item " + entity)
        val id = entity.id
        val item = client.createItem(entity)
        items += (id -> item)
    }
  }

  def start() = {
    game.start()
    updater.start()
  }

  override def entityRemoved(entity: Entity) =
    items.remove(entity.id) match {
      case Some(item) => client.removeItem(item)
      case _ => error("Tried to remove unknown MMO item " + entity)
    }

  def sendItems() = {
    println("Sending items..")
    items.keys foreach {
      id =>
        println("Sending item #" + id)
        val item = items(id)
        println("Sending item: " + item)
        try {
          val entity = game.entity(id)
          println("Sending entity: " + entity)
          val position = new Vec3D(entity.x, entity.y)
          client.setItemPosition(item, position)
        } catch {
          case e: Exception => error("Failed to send " + id + " to client.", e)
        }
    }
  }

}
