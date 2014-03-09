package com.quane.little.game.entity

import scala.collection.mutable.ListBuffer
import com.quane.little.game.LittleGameEngine

/** The cleaner is responsible for removing entities from the game.
  *
  * @param game the game
  */
class EntityCleaner(game: LittleGameEngine) {

  val queue = new ListBuffer[Entity]

  def remove(entity: Entity) {
    queue += entity
  }

  def cleanAll() {
    queue foreach (
      entity => {
        game.entities -= entity
        entity.isRemoved = true
      })
    queue.clear()
  }

}