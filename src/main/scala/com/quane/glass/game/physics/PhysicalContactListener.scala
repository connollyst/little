package com.quane.glass.game.physics

import java.lang.Override
import org.eintr.loglady.Logging
import org.jbox2d.callbacks.ContactImpulse
import org.jbox2d.callbacks.ContactListener
import org.jbox2d.collision.Manifold
import org.jbox2d.dynamics.Body
import org.jbox2d.dynamics.contacts.Contact
import com.quane.glass.language.event.GlassEvent
import com.quane.glass.game.entity.Entity
import java.lang.Override

class GlassContactListener
        extends ContactListener
        with Logging {

    @Override
    override def preSolve(contact: Contact, manifold: Manifold) {
        // do nothing
    }

    @Override
    override def postSolve(contact: Contact, impulse: ContactImpulse) {
        // do nothing
    }

    @Override
    override def beginContact(contact: Contact) {
        report(GlassEvent.OnContact, contact);
    }

    @Override
    override def endContact(contact: Contact) {
        report(GlassEvent.OnContactEnded, contact);
    }

    def report(event: GlassEvent, contact: Contact) {
        val fixtureA = contact.getFixtureA
        val fixtureB = contact.getFixtureB
        val bodyA = fixtureA.getBody
        val bodyB = fixtureB.getBody
        val entityA = getContactEntity(bodyA)
        val entityB = getContactEntity(bodyB)
        if (fixtureA.isSensor && fixtureB.isSensor) {
            // Two sensors do not substantiate an interaction
        } else if (fixtureA.isSensor) {
            // One sensor and one entity
            reportProximity(event, entityB, entityA)
        } else if (fixtureB.isSensor) {
            // One sensor and one entity
            reportProximity(event, entityA, entityB)
        } else {
            // Two entities
            reportContact(event, entityA, entityB)
        }
    }

    def reportProximity(event: GlassEvent, entity: Entity, sensor: Entity) {
        sensor.approachedBy(entity);
    }

    def reportContact(event: GlassEvent, entityA: Entity, entityB: Entity) {
        entityA.touchedBy(entityB)
        entityB.touchedBy(entityA)
    }

    def getContactEntity(body: Body): Entity = {
        body.getUserData.asInstanceOf[Entity]
    }

}