package com.quane.little.game.physics.bodies

import com.quane.little.game.entity.Entity
import org.jbox2d.dynamics.Body

class EntityBody(val physicalBody: Body) {

  private val coordinates = new Coordinates

  def attach(entity: Entity) {
    physicalBody.setUserData(entity)
  }

  def coords: Coordinates = {
    val position = physicalBody.getPosition
    coordinates.x = position.x
    coordinates.y = position.y
    coordinates
  }

  class Coordinates(var x: Float, var y: Float) {
    def this() = this(0f, 0f)
  }

}