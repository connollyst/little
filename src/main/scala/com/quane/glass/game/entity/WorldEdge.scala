package com.quane.glass.game.entity

import org.newdawn.slick.Graphics
import com.quane.glass.game.Game
import com.quane.glass.game.physics.bodies.EntityBody
import com.quane.glass.game.view.GameDrawer
import com.quane.glass.language.event.GlassEvent
import com.quane.glass.game.EventBus

class WorldEdge(body: EntityBody, game: Game, override val x: Float, override val y: Float, val w: Float, val h: Float)
        extends ImmovableEntity(body, game) {

    def touchedBy(other: Entity) {
        if (other isGuy) {
            EventBus.report(other.asInstanceOf[Mob], GlassEvent.OnContact);
        }
    }

    def render(graphics: Graphics) {
        GameDrawer.drawWall(graphics, this)
    }

}