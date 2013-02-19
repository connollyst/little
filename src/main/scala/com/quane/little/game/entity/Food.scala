package com.quane.little.game.entity

import org.newdawn.slick.Graphics
import com.quane.little.game.InteractionManager
import com.quane.little.game.physics.bodies.EntityBody
import com.quane.little.game.view.GameDrawer
import com.quane.little.game.EventBus
import com.quane.little.language.event.GlassEvent

class Food(body: EntityBody, manager: InteractionManager, val health: Int)
        extends Entity(body, manager) {

    var isConsumed = false;

    def touchedBy(other: Entity) {
        if (other isGuy) {
            consumedBy(other.asInstanceOf[Mob])
        }
    }

    def consumedBy(mob: Mob) {
        if (!isConsumed) {
            manager.mobConsumesFood(mob, this)
        }
    }

    def approachedBy(other: Entity) {
        if (other isGuy) {
            consumedBy(other.asInstanceOf[Mob])
        }
    }

    def approachedByMob(mob: Mob) {
        if (!isConsumed) {
            manager.mobConsumesFood(mob, this)
        }
    }

    def render(graphics: Graphics) {
        GameDrawer.drawFood(graphics, this)
    }

} 

