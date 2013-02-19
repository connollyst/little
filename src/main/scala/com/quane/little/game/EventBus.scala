package com.quane.little.game

import scala.collection.mutable.HashMap
import scala.collection.mutable.MultiMap
import scala.collection.mutable.Set
import com.quane.little.language.event.GlassEvent
import org.eintr.loglady.Logging
import com.quane.little.game.entity.Mob

class EventBus
        extends Logging {

    // Events that have occurred and are waiting to be handled
    val queue = new HashMap[Mob, Set[GlassEvent]]() with MultiMap[Mob, GlassEvent]

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
                        mob.operator.getEventListeners(event) foreach (
                            listener =>
                                listener.evaluate
                        )
                    })
            })
        queue.clear
    }

    def report(mob: Mob, event: GlassEvent) {
        queue.addBinding(mob, event)
    }

}