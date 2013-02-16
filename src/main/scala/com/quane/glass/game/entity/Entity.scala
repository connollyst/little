package com.quane.glass.game.entity

import com.quane.glass.game.Game
import org.newdawn.slick.Graphics
import com.quane.glass.game.physics.bodies.EntityBody

/** An entity, any object that exists in the game.
  *
  * Examples of entities include PC mobs, NPC mobs, food, game borders.
  *
  * @constructor create a new entity
  * @param body the entity's physical body
  * @param game the game this entity is involved in
  */
abstract class Entity(val body: EntityBody, val game: Game) {

    body.attach(this)

    val isRemoved = false

    def x: Float = {
        body.position.x
    }

    def y: Float = {
        body.position.y
    }

    def isGuy: Boolean = {
        false
    }

    def render(graphics: Graphics)

    def touchedBy(other: Entity)

}