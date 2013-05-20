package com.quane.little.game.entity

import com.quane.little.game.InteractionManager
import org.newdawn.slick.Graphics
import com.quane.little.game.view.GameDrawer
import com.quane.little.language.event.LittleEvent
import com.quane.little.game.physics.bodies.EntityBody
import org.eintr.loglady.Logging
import com.quane.little.language.Operator
import scala.util.Random

object Mob {

    val VAR_SPEED = "GuySpeed"

    val VAR_DIRECTION = "GuyDirection"

    val MAX_SPEED = 10

    val MIN_SPEED = 0
}

class Mob(body: EntityBody, manager: InteractionManager)
        extends Entity(body, manager)
        with Logging {

    val operator = new Operator(this)

    // Range of 1-10
    var speed = 0

    // Range of 0-360
    var direction = Random.nextInt(360)

    override def isGuy: Boolean = {
        // TODO I hate this
        true
    }

    def heal(amount: Int) {
        log.error("TODO heal by " + amount)
    }

    def touchedBy(other: Entity) {
        // TODO
    }

    def approachedBy(other: Entity) {
        // TODO
    }

    def render(graphics: Graphics) {
        GameDrawer.drawMob(graphics, this)
    }

}