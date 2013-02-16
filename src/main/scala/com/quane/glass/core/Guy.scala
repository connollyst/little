package com.quane.glass.core

import java.awt.Point
import java.lang.Override
import java.util.UUID
import scala.collection.mutable.ListBuffer
import org.eintr.loglady.Logging
import org.jbox2d.dynamics.Body
import org.newdawn.slick.Graphics
import com.quane.glass.core.event.EventListener
import com.quane.glass.core.event.GlassEvent
import com.quane.glass.core.language.Scope
import com.quane.glass.core.language.data.Direction
import com.quane.glass.core.language.data.Location
import com.quane.glass.core.language.data.Number
import com.quane.glass.core.language.data.Variable
import com.quane.glass.game.Game
import com.quane.glass.engine.GameDrawer
import com.quane.glass.game.entity.Entity
import com.quane.glass.game.view.GameDrawer

object Guy {

    val VAR_SPEED = "GuySpeed"

    val VAR_DIRECTION = "GuyDirection"

    val MAX_SPEED = 50

    val MIN_SPEED = 0
}

class Guy(body: Body, game: Game)
        extends Entity(body, game)
        with Scope
        with Logging {

    // TODO is there a 'community' scope above this?
    var scope: Scope = null;

    val uuid = UUID.randomUUID;

    // Range of 1-10
    var speed = 0;

    // Range of 0-360
    var direction = 0;

    def location: Location = {
        new Location(new Point(x toInt, y toInt))
    }

    @Override
    override def isGuy: Boolean = {
        // TODO I don't feel good about this..
        true
    }

    def touchedBy(other: Entity) {
        if (other isGuy) {
            touchedByOtherGuy(other.asInstanceOf[Guy])
        }
    }

    def touchedByOtherGuy(other: Guy) {
        log.error("TODO touched by another guy.. ewe!");
    }

    def heal(amount: Int) {
        log.error("TODO heal by " + amount);
    }

    val eventListeners = new ListBuffer[EventListener]

    def addEventListener(eventListener: EventListener) {
        log.info("Guy will listen for " + eventListener.event.getClass().getSimpleName() + " events.")
        // TODO set the function's scope to this guy
        eventListener.function.scope = this
        eventListeners += eventListener
    }

    def getEventListeners(event: GlassEvent): List[EventListener] = {
        val listening = new ListBuffer[EventListener]
        eventListeners.foreach(
            listener =>
                if (listener.event == event) {
                    listening += listener
                }
        )
        listening toList
    }

    def setSpeed(speed: Int) {
        this.speed = math.max(Guy.MIN_SPEED, math.min(speed, Guy.MAX_SPEED))
    }

    def setDirection(degrees: Int) {
        if (degrees < 0) {
            setDirection(360 + degrees)
        } else {
            this.direction = degrees % 360
        }
    }

    /* Intercept the Scope memory functions to access keyword variables */

    override def save(variable: Variable) {
        val name = variable.name
        log.info("Guy is saving " + name);
        if (name.equals(Guy.VAR_SPEED)) {
            setSpeed(
                variable.value match {
                    case number: Number =>
                        number.int
                    case other: Any =>
                        throw new ClassCastException(
                            "Cannot set " + Guy.VAR_SPEED + " to a "
                                + other.getClass.getSimpleName
                                + ", it must be a "
                                + classOf[Number].getSimpleName
                        )
                }
            )
        } else if (name.equals(Guy.VAR_DIRECTION)) {
            setDirection(
                variable.value match {
                    case direction: Direction =>
                        direction.degrees
                    case other: Any =>
                        throw new ClassCastException(
                            "Cannot set " + Guy.VAR_DIRECTION + " to a "
                                + other.getClass.getSimpleName
                                + ", it must be a "
                                + classOf[Direction].getSimpleName
                        )
                }
            )
        } else {
            // It's not a special variable, store it in normal memory
            super.save(variable);
        }
    }

    override def fetch(name: String): Variable = {
        log.info("Guy is remembering " + name);
        if (name.equals(Guy.VAR_SPEED)) {
            new Variable(name, new Number(speed))
        } else if (name.equals(Guy.VAR_DIRECTION)) {
            new Variable(name, new Direction(direction))
        } else {
            // It's not a special variable, fetch it from normal memory
            super.fetch(name);
        }
    }

    def render(graphics: Graphics) {
        GameDrawer.drawGuy(graphics, this)
    }

}
