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

  val eventBus: EventBus = new EventBus
  val engine: PhysicsEngine = new PhysicsEngine
  val builder = new BodyBuilder(this, engine.world)
  val cleaner: EntityCleaner = new EntityCleaner(this, engine)
  val entityFactory: EntityFactory = new EntityFactory(this)
  val entities: Map[String, Entity] = Map()
  val players: Map[String, Mob] = Map()

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
