package com.quane.little.game.entity

import com.quane.little.game.engine.InteractionManager

class WorldEdge(manager: InteractionManager)
  extends Entity(manager)
  with ImmovableEntity {

  def w: Float = 0.0f

  def h: Float = 0.0f

  override def touchedBy(other: Entity) {
    if (other.isGuy) {
      val mob = other.asInstanceOf[Mob]
      manager.mobContactsImmovableObject(mob)
    }
  }

  override def approachedBy(other: Entity) {
    // TODO
  }

}