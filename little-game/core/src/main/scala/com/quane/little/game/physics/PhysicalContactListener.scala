package com.quane.little.game.physics

import org.eintr.loglady.Logging
import com.quane.little.language.event.LittleEvent
import com.quane.little.game.entity.Entity
import java.lang.Override
import org.jbox2d.callbacks.{ContactImpulse, ContactListener}
import org.jbox2d.dynamics.contacts.Contact
import org.jbox2d.collision.Manifold
import org.jbox2d.dynamics.Body

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

  def report(event: LittleEvent, contact: Contact) {
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

  def reportProximity(event: LittleEvent, entity: Entity, sensor: Entity) {
    sensor.approachedBy(entity)
  }

  def reportContact(event: LittleEvent, entityA: Entity, entityB: Entity) {
    entityA.touchedBy(entityB)
    entityB.touchedBy(entityA)
  }

  def getContactEntity(body: Body): Entity = {
    body.getUserData.asInstanceOf[Entity]
  }

}