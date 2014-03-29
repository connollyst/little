package com.quane.little.game.entity

import scala.collection.mutable.ListBuffer
import com.quane.little.Logging

/** The remover is responsible for removing entities from the game and the
  * physics engine.
  */
class EntityRemover
  extends Logging {

  private val queue = new ListBuffer[Entity]
  private val listeners = new ListBuffer[EntityRemovalListener]

  def add(listener: EntityRemovalListener) = listeners += listener

  def remove(entity: Entity) = queue += entity

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