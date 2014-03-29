package com.quane.little.game.entity

import scala.collection.mutable.ListBuffer
import com.quane.little.game.LittleGameEngine
import com.quane.little.game.physics.PhysicsEngine

/** The remover is responsible for removing entities from the game and the
  * physics engine.
  *
  * @param game the game
  * @param engine the physics engine
  */
class EntityRemover(game: LittleGameEngine, engine: PhysicsEngine) {

  val queue = new ListBuffer[Entity]

  def remove(entity: Entity) {
    queue += entity
  }

  def cleanAll() {
    queue foreach (
      entity => {
        game.entities -= entity.uuid.toString
        entity.isRemoved = true
        engine.removeEntity(entity.body)
      })
    queue.clear()
  }

}