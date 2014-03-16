package com.quane.little.game.entity

import com.quane.little.game.physics.bodies.EntityBody
import com.quane.little.game.engine.InteractionManager
import com.quane.little.language.{Operable, Operator, Runtime}
import scala.util.Random
import com.google.common.base.Objects

class Mob(body: EntityBody, manager: InteractionManager)
  extends Entity(body, manager)
  with Operable {

  val operator = new Operator(new Runtime, this) // TODO where should the Runtime come from?

  // Range of 1-10
  private var _speed = 0

  // Range of 0-360
  private var _direction = Random.nextInt(360)

  override def speed: Int = _speed

  override def speed_=(mph: Int) {
    _speed = mph
  }

  override def direction: Int = _direction

  override def direction_=(degrees: Int) {
    _direction = degrees
  }

  override def isGuy: Boolean = {
    // TODO I hate this
    true
  }

  def heal(amount: Int) {
    // TODO
    println("TODO heal by " + amount)
  }

  override def touchedBy(other: Entity) {
    // TODO
  }

  override def approachedBy(other: Entity) {
    // TODO
  }

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("x", x)
      .add("y", y)
      .add("speed", speed)
      .add("direction", direction)
      .toString
}