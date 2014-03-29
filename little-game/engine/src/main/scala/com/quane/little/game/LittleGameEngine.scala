package com.quane.little.game

import com.quane.little.game.entity._
import scala.collection.mutable
import com.quane.little.game.physics.PhysicsEngine
import com.quane.little.game.physics.bodies.BodyBuilder
import com.quane.little.game.engine.EventBus
import com.quane.little.Logging

/** The little game engine maintains the state of the world and allows us to
  * step through it, advancing the physics simulation and evaluating little code
  * as appropriate.
  *
  * @author Sean Connolly
  */
class LittleGameEngine
  extends EntityRemovalListener
  with Logging {

  val hertz = 30.0
  val eventBus: EventBus = new EventBus
  val engine: PhysicsEngine = new PhysicsEngine
  val cleaner: EntityRemover = new EntityRemover
  val builder = new BodyBuilder(this, engine.world)
  val entityFactory: EntityFactory = new EntityFactory(this)

  val mobs: mutable.Map[String, Mob] = mutable.Map()
  val food: mutable.Map[String, Entity] = mutable.Map()
  val walls: mutable.Map[String, WorldEdge] = mutable.Map()

  val updater = new TimedUpdater(hertz) {
    def update() = updateState()
  }
  cleaner.add(this)
  cleaner.add(engine)

  def initialize() = {
    entityFactory.createMobs(5) foreach {
      mob =>
        mobs += (mob.uuid.toString -> mob)
    }
    entityFactory.createFoods() foreach {
      entity =>
        food += (entity.uuid.toString -> entity)
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
      warn("Skipping start game, already running.")
    }
  }

  def stop() = {
    updater.stop()
    info("Game stopped..")
  }

  def entity(id: String): Entity = {
    if (mobs.contains(id)) {
      mobs(id)
    } else if (food.contains(id)) {
      food(id)
    } else if (walls.contains(id)) {
      walls(id)
    } else {
      throw new IllegalAccessException("Nothing with id " + id)
    }
  }

  /** Update the game engine's state.
    */
  private def updateState(): Unit = {
    cleaner.cleanAll()
    engine.updateAll(mobs.values)
    eventBus.evaluateAll()
  }

  override def entityRemoved(entity: Entity) =
    food -= entity.uuid.toString

}
