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

  game.cleaner.register(this)

  def init(): Unit = {
    game.initialize()
    game.entities.values foreach {
      entity =>
        info("Adding game item " + entity)
        val id = entity.id
        val item = client.createItem(entity)
        items += (id -> item)
    }
  }

  def start(): Unit = {
    game.start()
    new Thread(updater).start()
  }

  def stop(): Unit = {
    updater.stop()
    game.stop()
  }

  /** Connect a player to the game.<br/>
    * A new [[com.quane.little.game.entity.Mob]] will be added to the game.
    *
    * @param username the username of the player
    * @return the game location of the connected player
    */
  def connect(username: String): Vec3D = {
    val mob = game.spawnPlayer(username)
    new Vec3D(mob.x, mob.y)
  }

  /** Disconnect a player from the game.<br/>
    * Their managed [[com.quane.little.game.entity.Mob]] will be removed from
    * the game.
    *
    * @param username the username of the player
    */
  def disconnect(username: String): Unit = game.removePlayer(username)

  /** Send [[com.smartfoxserver.v2.mmo.MMOItem]] position to the players.
    */
  def sendItems(): Unit =
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

  /** Handle the removal of an [[com.quane.little.game.entity.Entity]] from the
    * game simulation.
    *
    * @param entity the Entity which has been removed
    */
  override def entityRemoved(entity: Entity): Unit =
    items.remove(entity.id) match {
      case Some(item) => client.removeItem(item)
      case _ => error("Tried to remove unknown MMO item " + entity)
    }

}
