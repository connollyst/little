package com.quane.little.game.entity

import com.google.common.base.Objects
import com.quane.little.game.engine.InteractionManager
import com.quane.little.game.physics.bodies.EntityBody
import com.quane.little.language.Operator
import scala.util.Random

class Mob(body: EntityBody, manager: InteractionManager)
  extends Entity(body, manager) {

  // Range of 1-10
  private var _speed = 0

  // Range of 0-360
  private var _direction = Random.nextInt(360)

  override def speed: Int = _speed

  override def speed_=(mph: Int): Unit = _speed = mph

  override def direction: Int = _direction

  override def direction_=(degrees: Int): Unit = _direction = degrees

  // TODO I hate this
  override def isGuy: Boolean = true

  // TODO
  def heal(amount: Int): Unit = warn("TODO heal by " + amount)

  override def touchedBy(other: Entity): Unit = {
    super.touchedBy(other)
    other match {
      case food: Food => manager.mobConsumesFood(this, food)
      case wall: ImmovableEntity => manager.mobContactsImmovableObject(this, wall)
      case mob: Mob => manager.mobContactsMob(this, mob)
      case _ => error("Touched unexpected Entity: " + other)
    }
  }

  override def approachedBy(other: Entity): Unit = {
    super.approachedBy(other)
    other match {
      case food: Food => manager.mobApproachesFood(this, food)
      case wall: ImmovableEntity => manager.mobApproachesImmovableObject(this, wall)
      case mob: Mob => manager.mobApproachesMob(this, mob)
      case _ => error("Approaching unexpected Entity: " + other)
    }
  }

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("id", id)
      .add("x", x)
      .add("y", y)
      .add("speed", speed)
      .add("direction", direction)
      .toString

}