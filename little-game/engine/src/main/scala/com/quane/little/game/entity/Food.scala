package com.quane.little.game.entity

import com.quane.little.game.engine.InteractionManager

class Food(manager: InteractionManager, val health: Int)
  extends Entity(manager) {

  var isConsumed = false

  override def touchedBy(other: Entity) {
    if (other.isGuy) {
      consumedBy(other.asInstanceOf[Mob])
    }
  }

  def consumedBy(mob: Mob) {
    if (!isConsumed) {
      manager.mobConsumesFood(mob, this)
    }
  }

  override def approachedBy(other: Entity) {
    if (other.isGuy) {
      consumedBy(other.asInstanceOf[Mob])
    }
  }

  def approachedByMob(mob: Mob) {
    if (!isConsumed) {
      manager.mobConsumesFood(mob, this)
    }
  }

}

