package com.quane.glass.core

import java.awt.Point
import java.util.UUID

import scala.collection.mutable.ListBuffer
import scala.collection.mutable.MultiMap

import org.eintr.loglady.Logging
import org.jbox2d.dynamics.Body

import com.quane.glass.core.event.EventListener
import com.quane.glass.core.event.GlassEvent
import com.quane.glass.core.language.Scope
import com.quane.glass.core.language.data.Direction
import com.quane.glass.core.language.data.Location
import com.quane.glass.core.language.data.Number
import com.quane.glass.core.language.data.Variable

object Guy {

    val VAR_SPEED = "GuySpeed"

    val VAR_DIRECTION = "GuyDirection"

    val MAX_SPEED = 50

    val MIN_SPEED = 0
}

class Guy(val body: Body)
        extends Scope
        with Logging {

    // TODO the Guy is not the highest scope, yeah?
    var scope: Scope = null;

    val uuid = UUID.randomUUID;

    // TODO body and guy shouldn't be so directly attached
    if (body != null) { // it's only null in tests.. I'm drinking
        body.setUserData(uuid);
    }

    // Range of 1-10
    var speed = 0;

    // Range of 0-360
    var direction = 0;

    def location: Location = {
        val position = body.getPosition()
        val x = body.getPosition().x toInt;
        val y = body.getPosition().y toInt;
        new Location(new Point(x, y))
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

    def setSpeed(speed: Int): Unit = {
        this.speed = math.max(Guy.MIN_SPEED, math.min(speed, Guy.MAX_SPEED))
    }

    def setDirection(degrees: Int): Unit = {
        if (degrees < 0) {
            setDirection(360 + degrees)
        } else {
            this.direction = degrees % 360
        }
    }

    /* Intercept the Scope memory functions to access keyword variables */

    override def save(variable: Variable) = {
        log.info("Guy is saving " + variable.name);
        if (variable.name.equals(Guy.VAR_SPEED)) {
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
        } else if (variable.name.equals(Guy.VAR_DIRECTION)) {
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

}
