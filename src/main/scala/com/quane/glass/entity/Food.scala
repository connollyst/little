package com.quane.glass.entity

import org.jbox2d.dynamics.Body
import com.quane.glass.engine.Game
import com.quane.glass.core.event.GlassEvent
import com.quane.glass.core.Guy

class Food(body: Body, game: Game, health: Int)
        extends Entity(body, game) {

    def touchedBy(other: Entity) {
        if (other isGuy) {
            consumedBy(other.asInstanceOf[Guy])
        }
    }

    def consumedBy(guy: Guy) {
        game.eventBus.report(guy, GlassEvent.OnFoodConsumed)
        game.cleaner.remove(this)
        guy.heal(health)
    }
} 

