package com.quane.little.game.entity

import java.util.UUID

import com.quane.little.game.physics.bodies.EntityBody
import com.quane.little.game.view.{LineDrawer, ShapeDrawer, MeshDrawer, SpriteDrawer}
import com.quane.little.game.engine.InteractionManager

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

  def x: Float = {
    body.coords.x
  }

  def y: Float = {
    body.coords.y
  }

  def isGuy: Boolean = {
    false
  }

  def touchedBy(other: Entity)

  def approachedBy(other: Entity)

  def render(spriteDrawer: SpriteDrawer) {}

  def render(meshDrawer: MeshDrawer) {}

  def render(shapeDrawer: ShapeDrawer) {}

  def render(lineDrawer: LineDrawer) {}

}