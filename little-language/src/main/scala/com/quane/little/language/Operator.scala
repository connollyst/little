package com.quane.little.language

import com.quane.little.language.data.{Value, Variable}
import com.quane.little.language.event.Event.Event
import com.quane.little.language.event.EventListener

import scala.collection.mutable.ListBuffer

class Operator(override val runtime: Runtime, mob: Operable) extends Scope(runtime) {

  def x: Value = Value(mob.x)

  def y: Value = Value(mob.y)

  def speed: Value = Value(mob.speed)

  def speed_=(speed: Value): Unit =
    mob.speed = scala.math.max(Operable.MIN_SPEED, scala.math.min(speed.asNumber.asInt, Operable.MAX_SPEED))

  def direction: Value = Value(mob.direction)

  def direction_=(degrees: Value): Unit =
    if (degrees.asNumber < 0) {
      direction = degrees.asNumber + 360
    } else {
      mob.direction = (degrees.asNumber % 360).asInt
    }

  val eventListeners = ListBuffer[EventListener]()

  def addEventListener(eventListener: EventListener) = eventListeners += eventListener

  def getEventListeners(event: Event): Iterable[EventListener] =
    eventListeners.filter {
      listener => listener.event == event
    }

  /* Intercept the Scope memory functions to access keyword variables */

  override def save(variable: Variable) =
    variable.name match {
      case Operable.X => throw new IllegalAccessException("Cannot set X")
      case Operable.Y => throw new IllegalAccessException("Cannot set Y")
      case Operable.SPEED => speed = variable.value
      case Operable.DIRECTION => direction = variable.value
      case _ =>
        // It's not a special variable, store it in normal memory
        super.save(variable)
    }

  override def fetch(name: String): Variable =
    name match {
      case Operable.X => new Variable(name, x)
      case Operable.Y => new Variable(name, y)
      case Operable.SPEED => new Variable(name, speed)
      case Operable.DIRECTION => new Variable(name, direction)
      case _ =>
        // It's not a special variable, fetch it from normal memory
        super.fetch(name)
    }

}