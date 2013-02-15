package com.quane.glass.engine

import scala.collection.mutable.HashMap
import scala.collection.mutable.MultiMap
import scala.collection.mutable.Set
import com.quane.glass.core.Guy
import com.quane.glass.core.event.GlassEvent
import com.quane.glass.entity.Entity
import org.eintr.loglady.Logging

class EventBus
        extends Logging {

    // Events that have occurred and are waiting to be handled
    val queue = new HashMap[Guy, Set[GlassEvent]]() with MultiMap[Guy, GlassEvent]

    // TODO what a stupid fucking name..
    def unload() {
        // TODO check the time and only execute what we can in 1 step
        queue.keys foreach (
            entity => {
                // TODO replace with orElse?
                val events = queue.get(entity).orNull
                events foreach (
                    event => {
                        entity.getEventListeners(event) foreach (
                            listener =>
                                listener.evaluate
                        )
                    })
            })
        queue.clear
    }

    def report(guy: Guy, event: GlassEvent) {
        queue.addBinding(guy, event)
    }

}