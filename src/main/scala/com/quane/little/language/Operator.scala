package com.quane.little.language
import java.awt.Point
import java.lang.Override
import java.util.UUID

import scala.collection.mutable.ListBuffer

import org.eintr.loglady.Logging

import org.newdawn.slick.Graphics

import com.quane.little.game.Game
import com.quane.little.game.entity.Entity
import com.quane.little.game.view.GameDrawer
import com.quane.little.language.data.Direction
import com.quane.little.language.data.Location
import com.quane.little.language.data.Number
import com.quane.little.language.data.Variable
import com.quane.little.language.event.EventListener
import com.quane.little.language.event.GlassEvent
import com.quane.little.game.physics.bodies.EntityBody
import com.quane.little.game.entity.Mob

class Operator(mob: Mob)
        extends Scope
        with Logging {

    // TODO is there a 'community' scope above this?
    var scope: Scope = null;

    def location: Location = {
        new Location(new Point(mob.x toInt, mob.y toInt))
    }

    def speed(speed: Int) {
        mob.speed = Math.max(Mob.MIN_SPEED, Math.min(speed, Mob.MAX_SPEED))
    }

    def speed: Int = {
        mob.speed
    }

    def direction(degrees: Int) {
        if (degrees < 0) {
            direction(360 + degrees)
        } else {
            mob.direction = degrees % 360
        }
    }

    def direction: Int = {
        mob.direction
    }

    /* Listen for events */

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

    /* Intercept the Scope memory functions to access keyword variables */

    override def save(variable: Variable) {
        val name = variable.name
        log.info("Guy is saving " + name);
        if (name.equals(Mob.VAR_SPEED)) {
            speed(
                variable.value match {
                    case number: Number =>
                        number.int
                    case other: Any =>
                        throw new ClassCastException(
                            "Cannot set " + Mob.VAR_SPEED + " to a "
                                + other.getClass.getSimpleName
                                + ", it must be a "
                                + classOf[Number].getSimpleName
                        )
                }
            )
        } else if (name.equals(Mob.VAR_DIRECTION)) {
            direction(
                variable.value match {
                    case direction: Direction =>
                        direction.degrees
                    case other: Any =>
                        throw new ClassCastException(
                            "Cannot set " + Mob.VAR_DIRECTION + " to a "
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
        if (name.equals(Mob.VAR_SPEED)) {
            new Variable(name, new Number(mob.speed))
        } else if (name.equals(Mob.VAR_DIRECTION)) {
            new Variable(name, new Direction(mob.direction))
        } else {
            // It's not a special variable, fetch it from normal memory
            super.fetch(name);
        }
    }

}