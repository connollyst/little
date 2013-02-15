package com.quane.glass.engine

import java.lang.Override

import org.eintr.loglady.Logging
import org.jbox2d.callbacks.ContactImpulse
import org.jbox2d.callbacks.ContactListener
import org.jbox2d.collision.Manifold
import org.jbox2d.dynamics.Body
import org.jbox2d.dynamics.contacts.Contact

import com.quane.glass.core.event.GlassEvent
import com.quane.glass.entity.Entity

class GlassContactListener(eventBus: EventBus)
        extends ContactListener
        with Logging {

    @Override
    def preSolve(contact: Contact, manifold: Manifold) {
        // do nothing
    }

    @Override
    def postSolve(contact: Contact, impulse: ContactImpulse) {
        // do nothing
    }

    @Override
    def beginContact(contact: Contact) {
        reportContact(GlassEvent.OnContact, contact);
    }

    @Override
    def endContact(contact: Contact) {
        reportContact(GlassEvent.OnContactEnded, contact);
    }

    def reportContact(event: GlassEvent, contact: Contact) {
        val bodyA = contact.getFixtureA.getBody
        val bodyB = contact.getFixtureB.getBody
        
        val entityA = getContactEntity(bodyA)
        val entityB = getContactEntity(bodyB)
        
        entityA.touchedBy(entityB)
        entityB.touchedBy(entityA)
    }

    def getContactEntity(body: Body): Entity = {
        body.getUserData.asInstanceOf[Entity]
    }

}