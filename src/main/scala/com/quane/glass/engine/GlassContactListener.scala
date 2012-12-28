package com.quane.glass.engine

import org.jbox2d.callbacks.ContactImpulse
import org.jbox2d.callbacks.ContactListener
import org.jbox2d.collision.Manifold
import org.jbox2d.dynamics.contacts.Contact

import com.quane.glass.core.event.Event

class GlassContactListener(val game: Game) extends ContactListener {

    def beginContact(contact: Contact) = {
        reportContact(Event.OnContact, contact);
    }

    def endContact(contact: Contact) = {
        reportContact(Event.OnContactEnded, contact);
    }

    def reportContact(event: Event, contact: Contact) = {
        val bodyA = contact.getFixtureA.getBody
        val bodyB = contact.getFixtureB.getBody

        val userDataA = Option(bodyA.getUserData)
        val userDataB = Option(bodyB.getUserData)

        if (userDataA.isDefined) {
            reportContactWithUUID(event, userDataA toString)
        }
        if (userDataB.isDefined) {
            reportContactWithUUID(event, userDataB toString)
        }
    }

    def reportContactWithUUID(event: Event, uuid: String): Unit = {
        game.queueEvent(event, uuid);
    }

    def preSolve(contact: Contact, manifold: Manifold) = {
        // do nothing
    }

    def postSolve(contact: Contact, impulse: ContactImpulse) = {
        // do nothing
    }

}