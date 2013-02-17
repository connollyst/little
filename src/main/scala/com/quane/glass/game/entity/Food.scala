package com.quane.glass.game.entity

import org.newdawn.slick.Graphics
import com.quane.glass.game.Game
import com.quane.glass.game.physics.bodies.EntityBody
import com.quane.glass.game.view.GameDrawer
import com.quane.glass.game.EventBus
import com.quane.glass.language.event.GlassEvent

class Food(body: EntityBody, game: Game, health: Int)
        extends Entity(body, game) {

    var isConsumed = false;

    def touchedBy(other: Entity) {
        if (other isGuy) {
            consumedBy(other.asInstanceOf[Mob])
        }
    }

    def consumedBy(mob: Mob) {
        if (!isConsumed) {
            game.eventBus.report(mob, GlassEvent.OnFoodConsumed)
            game.cleaner.remove(this)
            mob.heal(health)
        }
    }

    def render(graphics: Graphics) {
        GameDrawer.drawFood(graphics, this)
    }

} 

