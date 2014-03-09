package com.quane.little.game

import com.quane.little.game.entity.{EntityCleaner, Mob, Entity, EntityFactory}
import scala.collection.mutable.ListBuffer
import com.quane.little.game.engine.EventBus

/** The little game engine maintains the state of the world and allows us to
  * step through it, advancing the physics simulation and evaluating little code
  * as appropriate.
  *
  * @author Sean Connolly
  */
class LittleGameEngine {

  val eventBus: EventBus = new EventBus
  val cleaner: EntityCleaner = new EntityCleaner(this)
  val entityFactory: EntityFactory = new EntityFactory(this)
  val entities = new ListBuffer[Entity]
  val players = new ListBuffer[Mob]

  def initialize() = {
    players ++= entityFactory.createMobs(5)
    entities ++= entityFactory.foodList()
  }

  def setEntityPosition(id: String, x: Float, y: Float): Mob = {
    // TODO store map instead of list
    players foreach {
      player =>
        if (player.uuid.toString == id) {
          player.x = x
          player.y = y
        }
        return player
    }
    throw new IllegalAccessException("No mob with id " + id)
  }

  /** Update the game engine's state.
    */
  def update() {
    cleaner.cleanAll()
    eventBus.evaluateAll()
  }

}
