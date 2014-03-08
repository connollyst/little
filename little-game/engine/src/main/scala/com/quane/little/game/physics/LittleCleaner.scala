package com.quane.little.game.physics

import scala.collection.mutable.ListBuffer
import com.quane.little.game.entity.Entity
import com.quane.little.game.LittleGameEngine

/** The cleaner is responsible for removing entities from the game and the
  * [[com.quane.little.game.physics.PhysicsEngine]]
  *
  * @param game the game
  * @param engine the physics engine
  */
class LittleCleaner(game: LittleGameEngine, engine: PhysicsEngine) {

  val queue = new ListBuffer[Entity]

  def remove(entity: Entity) {
    queue += entity
  }

  def cleanAll() {
    queue foreach (
      entity => {
        game.entities -= entity
        entity.isRemoved = true
        engine.removeEntity(entity.body)
      })
    queue.clear()
  }

}