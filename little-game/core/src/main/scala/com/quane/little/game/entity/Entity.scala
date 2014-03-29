package com.quane.little.game.entity

import com.google.common.base.Objects
import com.quane.little.Logging
import com.quane.little.game.engine.InteractionManager
import com.quane.little.game.physics.bodies.EntityBody
import java.util.UUID


/** An entity, any object that exists in the game.
  *
  * Examples of entities include PC mobs, NPC mobs, food, game borders.
  *
  * @constructor create a new entity
  * @param body the entity's physical body
  * @param manager the game's interaction manager
  */
abstract class Entity(val body: EntityBody, manager: InteractionManager)
  extends Logging {

  body.attach(this)

  val id = UUID.randomUUID.toString
  var isRemoved = false

  def x: Float = body.coords.x

  def y: Float = body.coords.y

  def isGuy = false

  def touchedBy(other: Entity): Unit = debug("Touched " + other)

  def approachedBy(other: Entity): Unit = debug("Approaching " + other)

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("x", x)
      .add("y", y)
      .add("id", id)
      .add("isGuy", isGuy)
      .toString

}