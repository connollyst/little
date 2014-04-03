package com.quane.little.game

import com.quane.little.game.engine.EventBus
import com.quane.little.game.entity._
import com.quane.little.game.physics.PhysicsEngine
import com.quane.little.game.physics.bodies.BodyBuilder
import com.quane.little.tools.Logging
import scala.collection.mutable

/** The game maintains the state of the world and all entities, tying together
  * the physics simulator with the Little data model and language evaluation.
  *
  * @author Sean Connolly
  */
class Game
  extends EntityRemovalListener
  with Logging {

  val hertz = 30.0
  val eventBus: EventBus = new EventBus
  val engine: PhysicsEngine = new PhysicsEngine
  val cleaner: EntityRemover = new EntityRemover
  val builder = new BodyBuilder(this, engine.world)
  val entityFactory: EntityFactory = new EntityFactory(this)

  val entities: mutable.Map[String, Entity] = mutable.Map()

  val updater = new TimedUpdater(hertz) {
    def update() = updateState()
  }
  cleaner.add(this)
  cleaner.add(engine)

  def initialize() = {
    entityFactory.createMobs(5) foreach {
      mob => entities += (mob.id -> mob)
    }
    entityFactory.createFoods() foreach {
      food => entities += (food.id -> food)
    }
    entityFactory.worldEdges() foreach {
      wall => entities += (wall.id -> wall)
    }
  }

  def start() = {
    if (!updater.isRunning) {
      new Thread(updater).start()
    } else {
      warn("Skipping start game, already running.")
    }
  }

  def stop() = {
    updater.stop()
    info("Game stopped..")
  }

  def entity(id: String): Entity = {
    if (entities.contains(id)) {
      entities(id)
    } else {
      throw new IllegalAccessException("No entity with id " + id)
    }
  }

  /** Update the game engine's state.
    */
  private def updateState(): Unit = {
    cleaner.cleanAll()
    engine.updateAll(entities.values)
    eventBus.evaluateAll()
  }

  override def entityRemoved(entity: Entity) =
    entities -= entity.id

}
