package com.quane.little.language

import com.quane.little.language.data.{Value, Variable}
import com.quane.little.language.event.EventListener
import com.quane.little.language.event.LittleEvent
import com.quane.little.tools.Logging
import scala.collection.mutable.ListBuffer

class Operator(override val runtime: Runtime, mob: Operable)
  extends Scope(runtime)
  with Logging {

  def x: Value = Value(mob.x toDouble)

  def y: Value = Value(mob.y toDouble)

  def speed(speed: Int) = mob.speed = Math.max(Operable.MIN_SPEED, Math.min(speed, Operable.MAX_SPEED))

  def speed: Int = mob.speed

  def direction(degrees: Value) {
    if (degrees.asInt < 0) {
      // TODO avoid creating a new Value here
      direction(Value(360 + degrees.asInt))
    } else {
      mob.direction = degrees.asInt % 360
    }
  }

  def direction: Value = Value(mob.direction)

  /* Listen for events */

  val eventListeners = new ListBuffer[EventListener]

  def addEventListener(eventListener: EventListener) {
    debug("Listening for " + eventListener.event.getClass.getSimpleName + " events.")
    eventListeners += eventListener
  }

  def getEventListeners(event: LittleEvent): List[EventListener] = {
    val listening = new ListBuffer[EventListener]
    eventListeners.foreach(
      listener =>
        if (listener.event == event) {
          listening += listener
        }
    )
    listening.toList
  }

  /* Intercept the Scope memory functions to access keyword variables */

  override def save(variable: Variable) {
    val name = variable.name
    name match {
      case Operable.X => throw new IllegalAccessException("Cannot set X")
      case Operable.Y => throw new IllegalAccessException("Cannot set Y")
      case Operable.SPEED => speed(variable.value.asInt)
      case Operable.DIRECTION => direction(variable.value)
      case _ =>
        // It's not a special variable, store it in normal memory
        super.save(variable)
    }
  }

  override def fetch(name: String): Variable = {
    val variable = name match {
      case Operable.X => new Variable(name, x)
      case Operable.Y => new Variable(name, y)
      case Operable.SPEED => new Variable(name, Value(mob.speed))
      case Operable.DIRECTION => new Variable(name, direction)
      case _ =>
        // It's not a special variable, fetch it from normal memory
        super.fetch(name)
    }
    variable
  }

}