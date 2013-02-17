package com.quane.glass.game.entity

import org.newdawn.slick.Graphics
import com.quane.glass.game.InteractionManager
import com.quane.glass.game.physics.bodies.EntityBody
import com.quane.glass.game.view.GameDrawer
import com.quane.glass.game.EventBus
import com.quane.glass.language.event.GlassEvent

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

    def render(graphics: Graphics) {
        GameDrawer.drawFood(graphics, this)
    }

} 

