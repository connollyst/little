package com.quane.little.game.physics

import com.quane.little.game.entity.Entity
import com.quane.little.tools.Logging
import org.jbox2d.callbacks.{ContactImpulse, ContactListener}
import org.jbox2d.collision.Manifold
import org.jbox2d.dynamics.Body
import org.jbox2d.dynamics.contacts.Contact
import com.quane.little.language.event.Event.Event
import com.quane.little.language.event.Event

/** A Box2D contact listener which translates physical body and sensor
  * interactions in the simulation to [[Entity]] level interactions.
  *
  * @author Sean Connolly
  */
class PhysicalContactListener
  extends ContactListener
  with Logging {

  override def preSolve(contact: Contact, manifold: Manifold) = {
    // do nothing
  }

  override def postSolve(contact: Contact, impulse: ContactImpulse) = {
    // do nothing
  }

  override def beginContact(contact: Contact) =
    reportInteraction(Event.OnContact, contact)

  override def endContact(contact: Contact) =
    reportInteraction(Event.OnContactEnded, contact)

  /** Report the interaction event.
    *
    * @param event the interaction event type
    * @param interaction the physical interaction data
    */
  private def reportInteraction(event: Event, interaction: Contact) {
    val fixtureA = interaction.getFixtureA
    val fixtureB = interaction.getFixtureB
    val bodyA = fixtureA.getBody
    val bodyB = fixtureB.getBody
    val entityA = getContactEntity(bodyA)
    val entityB = getContactEntity(bodyB)
    if (fixtureA.isSensor && fixtureB.isSensor) {
      // Two sensors do not substantiate an interaction
    } else if (fixtureA.isSensor || fixtureB.isSensor) {
      // Proximity: one sensor and one entity
      reportProximity(event, entityA, entityB)
    } else {
      // Contact: two entities
      reportContact(event, entityA, entityB)
    }
  }

  /** Report a proximity interaction event.<br/>
    * Proximity is defined by one entity interacting with the sensor of another.
    * <p>
    * <b>Note:</b> interactions are equally bi-directional
    * </p>
    *
    * @param event the interaction event type
    * @param entityA one of the interacting Entities
    * @param entityB the other interacting Entity
    */
  private def reportProximity(event: Event, entityA: Entity, entityB: Entity) {
    if (!entityA.isRemoved && !entityB.isRemoved) {
      // TODO handle proximity ending
      entityB.approachedBy(entityA)
      entityA.approachedBy(entityB)
    }
  }

  /** Report a contact interaction event.<br/>
    * Contact is defined by one entity interacting directly with another.
    * <p>
    * <b>Note:</b> interactions are equally bi-directional
    * </p>
    *
    * @param event the interaction event type
    * @param entityA one of the interacting Entities
    * @param entityB the other interacting Entity
    */
  private def reportContact(event: Event, entityA: Entity, entityB: Entity) {
    if (!entityA.isRemoved && !entityB.isRemoved) {
      // TODO handle contact ending
      entityA.touchedBy(entityB)
      entityB.touchedBy(entityA)
    }
  }

  private def getContactEntity(body: Body): Entity = body.getUserData.asInstanceOf[Entity]

}