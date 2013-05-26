package com.quane.little.language

object Operable {

  val VAR_SPEED = "GuySpeed"

  val VAR_DIRECTION = "GuyDirection"

  val MAX_SPEED = 50

  val MIN_SPEED = 0

}

/**
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
