package com.quane.little.game.physics

import com.quane.little.language.event.LittleEvent
import com.quane.little.game.entity.Entity
import java.lang.Override
import org.jbox2d.callbacks.{ContactImpulse, ContactListener}
import org.jbox2d.dynamics.contacts.Contact
import org.jbox2d.collision.Manifold
import org.jbox2d.dynamics.Body
import com.quane.little.game.Logging

class PhysicalContactListener
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
    report(LittleEvent.OnContact, contact)
  }

  @Override
  override def endContact(contact: Contact) {
    report(LittleEvent.OnContactEnded, contact)
  }

  private def report(event: LittleEvent, contact: Contact) {
    val fixtureA = contact.getFixtureA
    val fixtureB = contact.getFixtureB
    val bodyA = fixtureA.getBody
    val bodyB = fixtureB.getBody
    val entityA = getContactEntity(bodyA)
    val entityB = getContactEntity(bodyB)
    if (fixtureA.isSensor && fixtureB.isSensor) {
      // Two sensors do not substantiate an interaction
    } else if (fixtureA.isSensor || fixtureB.isSensor) {
      // One sensor and one entity
      reportProximity(entityA, entityB)
    } else {
      // Two entities
      reportContact(entityA, entityB)
    }
  }

  private def reportProximity(entity: Entity, sensor: Entity) {
    debug("Proximity: " + entity.uuid + " & " + sensor.uuid)
    sensor.approachedBy(entity)
    entity.approachedBy(sensor)
  }

  private def reportContact(entityA: Entity, entityB: Entity) {
    debug("Contact: " + entityA.uuid + " & " + entityB.uuid)
    entityA.touchedBy(entityB)
    entityB.touchedBy(entityA)
  }

  private def getContactEntity(body: Body): Entity = {
    body.getUserData.asInstanceOf[Entity]
  }

}