package com.quane.little.game.server

import scala.collection.mutable
import com.smartfoxserver.v2.mmo.{Vec3D, MMOItem}
import com.quane.little.game.entity.{EntityRemovalListener, Entity}
import com.quane.little.Logging
import com.quane.little.game.{TimedUpdater, Game}

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
        val id = entity.uuid
        val item = client.createItem(entity)
        addItem(id, item)
    }
  }

  def start() = {
    game.start()
    updater.start()
  }

  def addItem(uuid: String, item: MMOItem) =
    items += (uuid -> item)

  override def entityRemoved(entity: Entity) =
    items.remove(entity.uuid) match {
      case Some(item) => client.removeItem(item)
      case _ => error("Tried to remove unknown MMO item " + entity)
    }

  def sendItems() =
    items.keys foreach {
      id =>
        val item = items(id)
        try {
          val entity = game.entity(id)
          val x = entity.x
          val y = entity.y
          val position = new Vec3D(x, y)
          client.setItemPosition(item, position)
        } catch {
          case e: Exception => error("", e)
        }
    }

}
