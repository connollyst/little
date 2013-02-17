package com.quane.glass.game.entity

import java.util.UUID

import org.newdawn.slick.Graphics

import com.quane.glass.game.InteractionManager
import com.quane.glass.game.physics.bodies.EntityBody

/** An entity, any object that exists in the game.
  *
  * Examples of entities include PC mobs, NPC mobs, food, game borders.
  *
  * @constructor create a new entity
  * @param body the entity's physical body
  * @param game the game this entity is involved in
  */
abstract class Entity(val body: EntityBody, manager: InteractionManager) {

    body.attach(this)

    val uuid = UUID.randomUUID;

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

    def render(graphics: Graphics)

}