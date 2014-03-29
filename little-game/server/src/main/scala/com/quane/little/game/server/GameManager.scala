package com.quane.little.game.server

import scala.collection.mutable
import com.smartfoxserver.v2.mmo.{Vec3D, MMOItem}
import com.quane.little.game.entity.{EntityRemovalListener, Entity}
import com.quane.little.Logging
import com.quane.little.game.{TimedUpdater, LittleGameEngine}

class GameManager(client: ClientCommunicator)
  extends EntityRemovalListener
  with Logging {

  val items: mutable.Map[String, MMOItem] = mutable.Map()
  val game = new LittleGameEngine
  val updater = new Thread(new TimedUpdater(15) {
    def update() = sendItems()
  })

  def init() = {
    game.initialize()
    info("Initialized with "
      + game.players.size + " mobs, "
      + game.entities.size + " items, & "
      + game.walls.size + " walls.."
    )
  }

  def start() = {
    game.start()
    updater.start()
  }

  def addItem(uuid: String, item: MMOItem) =
    items += (uuid -> item)

  override def entityRemoved(entity: Entity) = {
    info("Removing MMO item " + entity)
    items.remove(entity.uuid) match {
      case Some(item) => client.removeItem(item)
      case _ => println("doodoododo"); error("Tried to remove unknown MMO item " + entity)
    }
  }

  def sendItems() = {
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

}
