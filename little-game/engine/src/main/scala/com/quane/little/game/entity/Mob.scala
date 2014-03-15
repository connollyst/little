package com.quane.little.game.entity

import com.quane.little.language.{Operable, Operator, Runtime}
import scala.util.Random
import com.quane.little.game.engine.InteractionManager
import com.google.common.base.Objects

class Mob(manager: InteractionManager)
  extends Entity(manager)
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
    println("mob touched by " + other)
    // TODO
  }

  override def approachedBy(other: Entity) {
    println("mob approached by " + other)
    // TODO
  }

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("id", uuid)
      .add("x", x)
      .add("y", y)
      .add("speed", speed)
      .add("direction", direction)
      .toString

}