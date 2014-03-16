package com.quane.little.game

import com.quane.little.game.entity._
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

  val hertz = 30.0
  val eventBus: EventBus = new EventBus
  val engine: PhysicsEngine = new PhysicsEngine
  val builder = new BodyBuilder(this, engine.world)
  val cleaner: EntityCleaner = new EntityCleaner(this, engine)
  val entityFactory: EntityFactory = new EntityFactory(this)
  val entities: Map[String, Entity] = Map()
  val walls: Map[String, WorldEdge] = Map()
  val players: Map[String, Mob] = Map()
  val updater = new TimedUpdater(hertz) {
    def update() = updateState()
  }

  def initialize() = {
    entityFactory.createMobs(5) foreach {
      player =>
        players += (player.uuid.toString -> player)
    }
    entityFactory.createFoods() foreach {
      entity =>
        entities += (entity.uuid.toString -> entity)
    }
    entityFactory.worldEdges() foreach {
      wall =>
        walls += (wall.uuid.toString -> wall)
    }
  }

  def start() = {
    if (!updater.isRunning) {
      new Thread(updater).start()
    } else {
      println("Skipping start game, already running.")
    }
  }

  def stop() = {
    updater.stop()
    println("Game stopped..")
  }

  /** Update the game engine's state.
    */
  private def updateState(): Unit = {
    cleaner.cleanAll()
    engine.updateAll(players.values)
    eventBus.evaluateAll()
  }

}
