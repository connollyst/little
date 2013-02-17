package com.quane.glass.game.entity

import org.newdawn.slick.Graphics
import com.quane.glass.game.InteractionManager
import com.quane.glass.game.physics.bodies.EntityBody
import com.quane.glass.game.view.GameDrawer
import com.quane.glass.language.event.GlassEvent
import com.quane.glass.game.physics.bodies.StaticBody

class WorldEdge(override val body: StaticBody, manager: InteractionManager)
        extends Entity(body, manager)
        with ImmovableEntity {

    def touchedBy(other: Entity) {
        if (other isGuy) {
            val mob = other.asInstanceOf[Mob]
            manager.mobContactsImmovableObject(mob)
        }
    }

    def approachedBy(other: Entity) {
        // TODO
    }

    def render(graphics: Graphics) {
        GameDrawer.drawWall(graphics, this)
    }

}