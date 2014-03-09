package com.quane.little.game.entity

import java.util.UUID

import com.quane.little.game.engine.InteractionManager

/** An entity, any object that exists in the game.
  *
  * Examples of entities include PC mobs, NPC mobs, food, game borders.
  *
  * @constructor create a new entity
  * @param manager the game's interaction manager
  */
abstract class Entity(manager: InteractionManager) {

  val uuid = UUID.randomUUID

  var isRemoved = false

  var x: Float = 0.0f
  var y: Float = 0.0f

  def isGuy: Boolean = false

  def touchedBy(other: Entity)

  def approachedBy(other: Entity)

}