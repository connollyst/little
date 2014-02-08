package com.quane.little.language

import scala.collection.mutable.ListBuffer

import org.eintr.loglady.Logging


import com.quane.little.language.data.{Value, Variable}
import com.quane.little.language.event.EventListener
import com.quane.little.language.event.LittleEvent

class Operator(override val runtime: Runtime, mob: Operable)
  extends Scope
  with Logging {

  var scope: Scope = runtime

  def x: Value = new Value(mob.x toDouble)

  def y: Value = new Value(mob.y toDouble)

  def speed(speed: Int) = mob.speed = Math.max(Operable.MIN_SPEED, Math.min(speed, Operable.MAX_SPEED))

  def speed: Int = mob.speed

  def direction(degrees: Value) {
    if (degrees.asInt < 0) {
      // TODO avoid creating a new Value here
      direction(new Value(360 + degrees.asInt))
    } else {
      mob.direction = degrees.asInt % 360
    }
  }

  def direction: Value = new Value(mob.direction)

  /* Listen for events */

  val eventListeners = new ListBuffer[EventListener]

  def addEventListener(eventListener: EventListener) {
    log.debug(
      "Guy will listen for " + eventListener.event.getClass.getSimpleName + " events."
    )
    // TODO set the function's scope to this guy
    eventListener.function.scope = this
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
    log.debug("Guy is saving " + name)
    name match {
      case Operable.X => throw new IllegalAccessException("Cannot set X")
      case Operable.Y => throw new IllegalAccessException("Cannot set Y")
      case Operable.VAR_SPEED => speed(variable.value.asInt)
      case Operable.VAR_DIRECTION => direction(variable.value)
      case _ =>
        // It's not a special variable, store it in normal memory
        super.save(variable)
    }
  }

  override def fetch(name: String): Variable = {
    log.debug("Guy is returning " + name)
    name match {
      case Operable.X => new Variable(name, x)
      case Operable.Y => new Variable(name, y)
      case Operable.VAR_SPEED => new Variable(name, new Value(mob.speed))
      case Operable.VAR_DIRECTION => new Variable(name, direction)
      case _ =>
        // It's not a special variable, fetch it from normal memory
        super.fetch(name)
    }
  }

}