package com.quane.little.game.entity

import com.quane.little.game.engine.InteractionManager

class Food(manager: InteractionManager, val health: Int)
  extends Entity(manager) {

  override def touchedBy(other: Entity) {
    println("food touched by " + other)
    if (other.isGuy) {
      consumedBy(other.asInstanceOf[Mob])
    }
  }

  def consumedBy(mob: Mob) {
    println("food consumed by " + mob)
    manager.mobConsumesFood(mob, this)
  }

  override def approachedBy(other: Entity) {
    println("food approached by " + other)
    if (other.isGuy) {
      consumedBy(other.asInstanceOf[Mob])
    }
  }

  def approachedByMob(mob: Mob) {
    println("food approached by " + mob)
    manager.mobConsumesFood(mob, this)
  }

}
