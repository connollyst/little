package com.quane.little.language.event

import com.google.common.base.Objects
import com.quane.little.language._
import com.quane.little.language.event.Event.Event

/** Evaluates a block of code in response to an [[com.quane.little.language.event.Event]].
  *
  * @param event the event to listen for
  */
class EventListener(val event: Event, block: Block = new Block)
  extends Code {

  def stepCount: Int = block.length

  def addStep(step: EvaluableCode): EventListener = {
    block.addStep(step)
    this
  }

  def steps: List[EvaluableCode] = block.steps

  def steps_=(steps: List[EvaluableCode]) = block.steps = steps

  /** Evaluate the event listener; which should only be done when the event has occurred.<br/>
    * Note: the listener defines it's own scope within the evaluation scope
    *
    * @param scope the primary object if the event (eg: the player)
    * @param it the secondary object in the event (eg: some food)
    */
  def evaluate(scope: Operator, it: Operator): Unit = {
    val listenerScope = new Scope(scope)
    // TODO can these be put into anonymous functions?
    listenerScope.save("_little_it_x", it.x)
    listenerScope.save("_little_it_y", it.y)
    listenerScope.save("_little_it_speed", it.speed)
    listenerScope.save("_little_it_direction", it.direction)
    block.evaluate(listenerScope)
  }

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("event", event)
      .add("block", block)
      .toString

}