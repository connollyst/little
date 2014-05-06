package com.quane.little.game.entity

import com.quane.little.tools.Logging
import scala.collection.mutable.ListBuffer

/** Responsible for removing Entities from the game.
  *
  * @author Sean Connolly
  */
class EntityRemover
  extends Logging {

  private val queue = new ListBuffer[Entity]
  private val listeners = new ListBuffer[EntityRemovalListener]

  def add(listener: EntityRemovalListener) = listeners += listener

  def remove(entity: Entity) = {
    debug("Queued entity to be removed from simulation: " + entity)
    queue += entity
  }

  def cleanAll() {
    queue foreach (
      entity => {
        debug("Marking as removed: " + entity)
        entity.isRemoved = true
        listeners.foreach {
          listener =>
            debug("Informing listener that Entity was removed: " + listener)
            listener.entityRemoved(entity)
        }
      })
    queue.clear()
  }

}

/** Listener for when an Entity is removed from the game.
  *
  * @author Sean Connolly
  */
trait EntityRemovalListener {

  /** Called when the Entity has been marked as removed.
    *
    * @param entity the Entity which has been removed
    */
  def entityRemoved(entity: Entity)

}