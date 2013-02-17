package com.quane.glass.game.entity

import com.quane.glass.game.Game
import org.newdawn.slick.Graphics
import com.quane.glass.game.view.GameDrawer
import com.quane.glass.language.event.GlassEvent
import com.quane.glass.game.physics.bodies.EntityBody
import org.eintr.loglady.Logging
import com.quane.glass.language.Operator

object Mob {

    val VAR_SPEED = "GuySpeed"

    val VAR_DIRECTION = "GuyDirection"

    val MAX_SPEED = 50

    val MIN_SPEED = 0
}

class Mob(body: EntityBody, game: Game)
        extends Entity(body, game)
        with Logging {

    val operator = new Operator(this)

    // Range of 1-10
    var speed = 0;

    // Range of 0-360
    var direction = 0;

    override def isGuy: Boolean = {
        // TODO I hate this
        true
    }

    def heal(amount: Int) {
        log.error("TODO heal by " + amount);
    }

    def touchedBy(other: Entity) {
        // TODO
    }

    def render(graphics: Graphics) {
        // TODO
    }

}