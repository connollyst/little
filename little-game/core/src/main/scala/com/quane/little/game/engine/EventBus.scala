package com.quane.little.game.engine

import com.quane.little.game.entity.{Entity, Mob}
import scala.collection.mutable
import com.quane.little.tools.Logging
import com.quane.little.language.event.Event.Event

/** Manages the processing of each [[com.quane.little.language.event.Event]].
  * Events can be reported at any time, and are then processed between time steps.
  *
  * @author Sean Connolly
  */
class EventBus extends Logging {

  // Events that have occurred and are waiting to be handled
  val queue = new mutable.HashMap[Mob, mutable.Set[Pair[Event, Entity]]]() with mutable.MultiMap[Mob, Pair[Event, Entity]]

  /** Report an event, queuing it to be processed at an appropriate time.
    *
    * @param event the event which occurred
    * @param mob the mob to which the event occurred
    * @param it the secondary entity in the event (eg: food, another mob)
    */
  def report(event: Event, mob: Mob, it: Entity): Unit = queue.addBinding(mob, event -> it)

  /** Evaluate all queued events.
    */
  def evaluateAll() {
    // TODO check the time and only execute what we can in 1 step
    queue.keys foreach (
      mob => {
        // TODO replace with orElse?
        val events = queue.get(mob).orNull
        events foreach {
          case (event, it) =>
            mob.operator.getEventListeners(event) foreach {
              listener =>
                debug("Evaluating " + event + " listener for " + mob.id + ": " + listener)
                listener.evaluate(mob.operator, it.operator)
            }
        }
      })
    queue.clear()
  }

}