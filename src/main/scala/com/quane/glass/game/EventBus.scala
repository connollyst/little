package com.quane.glass.game

import scala.collection.mutable.HashMap
import scala.collection.mutable.MultiMap
import scala.collection.mutable.Set
import com.quane.glass.language.event.GlassEvent
import org.eintr.loglady.Logging
import com.quane.glass.game.entity.Mob

object EventBus
        extends Logging {

    // Events that have occurred and are waiting to be handled
    val queue = new HashMap[Mob, Set[GlassEvent]]() with MultiMap[Mob, GlassEvent]

    def evaluateAll() {
        // TODO check the time and only execute what we can in 1 step
        queue.keys foreach (
            entity => {
                // TODO replace with orElse?
                val events = queue.get(entity).orNull
                events foreach (
                    event => {
                        entity.operator.getEventListeners(event) foreach (
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