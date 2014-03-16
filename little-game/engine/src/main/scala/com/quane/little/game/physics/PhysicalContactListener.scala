package com.quane.little.game.physics

import com.quane.little.language.event.LittleEvent
import com.quane.little.game.entity.Entity
import java.lang.Override
import org.jbox2d.callbacks.{ContactImpulse, ContactListener}
import org.jbox2d.dynamics.contacts.Contact
import org.jbox2d.collision.Manifold
import org.jbox2d.dynamics.Body
import org.slf4j.LoggerFactory

class PhysicalContactListener
  extends ContactListener {

  val logger = LoggerFactory.getLogger(getClass)

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
      logger.debug("(interaction between two sensors, ignoring: " + entityA + " & " + entityB + ")")
    } else if (fixtureA.isSensor) {
      // One sensor and one entity
      reportProximity(entityB, entityA)
    } else if (fixtureB.isSensor) {
      // One sensor and one entity
      reportProximity(entityA, entityB)
    } else {
      // Two entities
      reportContact(entityA, entityB)
    }
  }

  private def reportProximity(entity: Entity, sensor: Entity) {
    logger.error("Proximity: " + entity + " & " + sensor)
    sensor.approachedBy(entity)
  }

  private def reportContact(entityA: Entity, entityB: Entity) {
    logger.error("Contact: " + entityA + " & " + entityB)
    entityA.touchedBy(entityB)
    entityB.touchedBy(entityA)
  }

  private def getContactEntity(body: Body): Entity = {
    body.getUserData.asInstanceOf[Entity]
  }

}