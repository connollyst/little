package com.quane.glass.game.entity


import com.quane.glass.game.Game
import com.quane.glass.language.event.GlassEvent
import com.quane.glass.core.Guy
import org.newdawn.slick.Graphics
import com.quane.glass.game.view.GameDrawer
import com.quane.glass.game.physics.bodies.EntityBody

class WorldEdge(body: EntityBody, game: Game, override val x: Float, override val y: Float, val w: Float, val h: Float)
        extends Entity(body, game) {

    def touchedBy(other: Entity) {
        if (other isGuy) {
            game.eventBus.report(other.asInstanceOf[Guy], GlassEvent.OnContact);
        }
    }

    def render(graphics: Graphics) {
        GameDrawer.drawWall(graphics, this)
    }

}