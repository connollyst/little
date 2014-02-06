package com.quane.little.language

import scala.collection.mutable.ListBuffer

import org.eintr.loglady.Logging


import com.quane.little.language.data.{Value, Variable}
import com.quane.little.language.event.EventListener
import com.quane.little.language.event.LittleEvent

class Operator(mob: Operable)
  extends Scope
  with Logging {

  // TODO is there a 'community' scope above this?
  var scope: Scope = null

  def x: Value = new Value(mob.x toInt)

  def y: Value = new Value(mob.y toInt)

  def speed(speed: Int) {
    mob.speed = Math.max(Operable.MIN_SPEED, Math.min(speed, Operable.MAX_SPEED))
  }

  def speed: Int = {
    mob.speed
  }

  def direction(degrees: Value) {
    if (degrees.asNumber < 0) {
      // TODO avoid creating a new Number here
      direction(new Value(360 + degrees.asNumber))
    } else {
      mob.direction = degrees.asNumber % 360
    }
  }

  def direction: Value = {
    new Value(mob.direction)
  }

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
    if (name.equals(Operable.VAR_SPEED)) {
      speed(variable.value.asNumber)
    } else if (name.equals(Operable.VAR_DIRECTION)) {
      direction(variable.value)
    } else {
      // It's not a special variable, store it in normal memory
      super.save(variable)
    }
  }

  override def fetch(name: String): Variable = {
    log.debug("Guy is returning " + name)
    if (name.equals(Operable.VAR_SPEED)) {
      log.debug("mob: " + mob)
      mob.speed
      name
      new Variable(name, new Value(mob.speed))
    } else if (name.equals(Operable.VAR_DIRECTION)) {
      new Variable(name, new Value(mob.direction))
    } else {
      // It's not a special variable, fetch it from normal memory
      super.fetch(name)
    }
  }

}