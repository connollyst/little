package com.quane.glass.core

import java.util.UUID
import org.jbox2d.dynamics.Body
import com.quane.glass.core.event.Event
import com.quane.glass.core.event.EventListener
import org.eintr.loglady.Logging
import com.quane.glass.core.language.data.Variable
import com.quane.glass.core.language.Scope

object Guy {

    val VAR_SPEED = "GuySpeed"

    val VAR_DIRECTION = "GuyDirection"

}

class Guy(val body: Body)
        extends Scope
        with Logging {

    // TODO the Guy is not the highest scope, yeah?
    val scope: Scope = null;

    val uuid = UUID.randomUUID;

    // TODO body and guy shouldn't be so directly attached
    if (body != null) { // it's only null in tests.. I'm drinking
        body.setUserData(uuid);
    }

    // Range of 1-10
    var speed = 0;

    // Range of 0-360
    var direction = 0;

    var eventListeners = List[EventListener]();

    def addEventListener(eventListener: EventListener) {
        eventListeners = eventListener :: eventListeners;
    }

    def getEventListeners(event: Event): List[EventListener] = {
        var listening = List[EventListener]();
        eventListeners.foreach(listener => if (listener.event == event) {
            listening = listener :: listening;
        });
        listening;
    }

    def setSpeed(speed: Int): Unit = {
        // TODO check for limit violations
        this.speed = speed
    }

    def setDirection(direction: Int): Unit = {
        if (direction < 0) {
            setDirection(360 + direction)
        } else {
            this.direction = direction % 360
        }
    }

    /* Intercept the Scope memory functions to access keyword variables */

    override def save(variable: Variable) = {
        log.info("Guy is saving " + variable.name);
        if (variable.name.equals(Guy.VAR_SPEED)) {
            speed = Integer valueOf (variable.value toString)
        } else if (variable.name.equals(Guy.VAR_DIRECTION)) {
            direction = Integer valueOf (variable.value toString)
        } else {
            // It's not a special variable, store it in normal memory
            super.save(variable);
        }
    }

    override def fetch(name: String): Variable = {
        log.info("Guy is remembering " + name);
        if (name.equals(Guy.VAR_SPEED)) {
            new Variable(name, speed toString)
        } else if (name.equals(Guy.VAR_DIRECTION)) {
            new Variable(name, direction toString)
        } else {
            // It's not a special variable, fetch it from normal memory
            super.fetch(name);
        }
    }

}
