package com.quane.little.language

object Operable {

  val X = "_little_x"

  val Y = "_little_y"

  val SPEED = "_little_speed"

  val DIRECTION = "_little_direction"

  val MAX_SPEED = 50

  val MIN_SPEED = 0

}

/** An object that can be operated: has a location, a speed, and a direction.
  *
  * @author Sean Connolly
  */
trait Operable {

  def x: Float

  def y: Float

  def speed: Int

  def speed_=(mph: Int)

  def direction: Int

  def direction_=(degrees: Int)

}
