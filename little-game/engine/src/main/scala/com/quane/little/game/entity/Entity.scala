package com.quane.little.game.entity

import java.util.UUID

import com.quane.little.game.physics.bodies.EntityBody
import com.quane.little.game.engine.InteractionManager
import com.google.common.base.Objects

/** An entity, any object that exists in the game.
  *
  * Examples of entities include PC mobs, NPC mobs, food, game borders.
  *
  * @constructor create a new entity
  * @param body the entity's physical body
  * @param manager the game's interaction manager
  */
abstract class Entity(val body: EntityBody, manager: InteractionManager) {

  body.attach(this)

  val uuid = UUID.randomUUID

  var isRemoved = false

  def x: Float = body.coords.x

  def y: Float = body.coords.y

  def isGuy = false

  def touchedBy(other: Entity)

  def approachedBy(other: Entity)

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("x", x)
      .add("y", y)
      .add("uuid", uuid)
      .add("isGuy", isGuy)
      .toString

}