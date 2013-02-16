package com.quane.glass.game.entity

import com.quane.glass.game.Game
import com.quane.glass.core.Guy
import org.newdawn.slick.Graphics
import com.quane.glass.game.view.GameDrawer
import com.quane.glass.language.event.GlassEvent
import com.quane.glass.game.physics.bodies.EntityBody

class Food(body: EntityBody, game: Game, health: Int)
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

    def render(graphics: Graphics) {
        GameDrawer.drawFood(graphics, this)
    }

} 

