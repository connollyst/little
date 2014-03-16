package com.quane.little.game

import com.quane.little.game.entity.{EntityCleaner, Mob, Entity, EntityFactory}
import scala.collection.mutable.Map
import com.quane.little.game.physics.PhysicsEngine
import com.quane.little.game.physics.bodies.BodyBuilder
import com.quane.little.game.engine.EventBus

/** The little game engine maintains the state of the world and allows us to
  * step through it, advancing the physics simulation and evaluating little code
  * as appropriate.
  *
  * @author Sean Connolly
  */
class LittleGameEngine {

  val GAME_HERTZ = 30.0
  val TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ
  val MAX_UPDATES_BEFORE_RENDER = 5

  val eventBus: EventBus = new EventBus
  val engine: PhysicsEngine = new PhysicsEngine
  val builder = new BodyBuilder(this, engine.world)
  val cleaner: EntityCleaner = new EntityCleaner(this, engine)
  val entityFactory: EntityFactory = new EntityFactory(this)
  val entities: Map[String, Entity] = Map()
  val players: Map[String, Mob] = Map()

  private var isRunning = false
  private var lastUpdateTime: Double = System.nanoTime()

  def initialize() = {
    entityFactory.createMobs(5) foreach {
      player =>
        players += (player.uuid.toString -> player)
    }
    entityFactory.foodList() foreach {
      entity =>
        entities += (entity.uuid.toString -> entity)
    }
    entityFactory.worldEdges() foreach {
      edge =>
        entities += (edge.uuid.toString -> edge)
    }
    update()
  }

  def start() = {
    if (!isRunning) {
      isRunning = true
      new Thread() {
        override def run() {
          println("Starting game..")
          runSimulation()
        }
      }.start()
    } else {
      println("Skipping start game, already running.")
    }
  }

  def stop() = {
    if (isRunning) {
      isRunning = false
      println("Stopped game..")
    } else {
      println("Skipping stop game, not running.")
    }
  }

  /** Run the game simulation.
    * TODO: follow thread at http://www.java-gaming.org/index.php?topic=24220.0
    */
  private def runSimulation() = {
    while (isRunning) {
      var now = System.nanoTime()
      var updateCount = 0
      // Do as many game updates as we need to, potentially playing catchup.
      while (now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER) {
        update()
        lastUpdateTime += TIME_BETWEEN_UPDATES
        updateCount += 1
      }
      println("Updated simulation " + updateCount + " times.")
      // If for some reason an update takes forever, we don't want to do an insane number of catchups.
      // If you were doing some sort of game that needed to keep EXACT time, you would get rid of this.
      if (now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
        lastUpdateTime = now - TIME_BETWEEN_UPDATES.toLong
      }
      // Yield until it has been at least the target time between renders. This saves the CPU from hogging.
      while (now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
        Thread.`yield`()
        // This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
        // You can remove this line and it will still work (better), your CPU just climbs on certain OSes.
        // FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a look at different peoples' solutions to this.
        try {
          Thread.sleep(1)
        }
        now = System.nanoTime()
      }
    }
  }

  private def entity(id: String): Entity = {
    if (players.contains(id)) {
      players(id)
    } else if (entities.contains(id)) {
      entities(id)
    } else {
      throw new IllegalAccessException("Nothing with id " + id)
    }
  }

  /** Update the game engine's state.
    */
  private def update(): Unit = {
    cleaner.cleanAll()
    engine.updateAll(players.values)
    eventBus.evaluateAll()
  }

}
