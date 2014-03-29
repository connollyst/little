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
  val listeners = new ListBuffer[EntityRemovalListener]

  def remove(entity: Entity) {
    queue += entity
  }

  def cleanAll() {
    queue foreach (
      entity => {
        entity.isRemoved = true
        listeners.foreach {
          listener => listener.entityRemoved(entity)
        }
      })
    queue.clear()
  }

}

trait EntityRemovalListener {

  def entityRemoved(entity: Entity)

}