package com.quane.little.game.entity

import com.quane.little.game.physics.bodies.EntityBody
import com.quane.little.game.engine.InteractionManager

class Food(body: EntityBody, manager: InteractionManager, val health: Int)
  extends Entity(body, manager) {

  var isConsumed = false

  override def touchedBy(other: Entity) {
    if (other.isGuy) {
      consumedByMob(other.asInstanceOf[Mob])
    }
  }

  override def approachedBy(other: Entity) {
    if (other.isGuy) {
      approachedByMob(other.asInstanceOf[Mob])
    }
  }

  def consumedByMob(mob: Mob) {
    if (!isConsumed) {
      info("Consumed by " + mob)
      manager.mobConsumesFood(mob, this)
    }
  }

  def approachedByMob(mob: Mob) {
    if (!isConsumed) {
      info("Approached by " + mob)
      manager.mobApproachesFood(mob, this)
    }
  }

}

