package com.quane.little.game.engine

import com.quane.little.game.entity.Mob
import com.quane.little.language.event.LittleEvent
import scala.collection.mutable
import com.quane.little.tools.Logging

class EventBus extends Logging {

  // Events that have occurred and are waiting to be handled
  val queue = new mutable.HashMap[Mob, mutable.Set[LittleEvent]]() with mutable.MultiMap[Mob, LittleEvent]

  def report(mob: Mob, event: LittleEvent): Unit = queue.addBinding(mob, event)

  /** Evaluate all queued events.
    */
  def evaluateAll() {
    // TODO check the time and only execute what we can in 1 step
    queue.keys foreach (
      mob => {
        // TODO replace with orElse?
        val events = queue.get(mob).orNull
        events foreach (
          event => {
            mob.operator.getEventListeners(event) foreach {
              listener =>
                debug("Evaluating " + event + " listener for " + mob.id + ": " + listener)
                listener.evaluate(mob.operator)
            }
          })
      })
    queue.clear()
  }

}