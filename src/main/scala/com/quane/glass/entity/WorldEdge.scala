package com.quane.glass.entity

import org.jbox2d.dynamics.Body
import com.quane.glass.engine.Game
import com.quane.glass.core.event.GlassEvent
import com.quane.glass.core.Guy
import com.quane.glass.engine.GameDrawer
import org.newdawn.slick.Graphics

class WorldEdge(body: Body, game: Game, override val x: Float, override val y: Float, val w: Float, val h: Float)
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