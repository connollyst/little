package com.quane.glass.game.physics.bodies

import org.jbox2d.dynamics.Body
import com.quane.glass.game.entity.Entity
import org.jbox2d.common.Vec2

class EntityBody(val physicalBody: Body) {

    private val coordinates = new Coordinates

    def attach(entity: Entity) {
        physicalBody.setUserData(entity)
    }

    def coords: Coordinates = {
        val position = physicalBody.getPosition();
        coords.x = position.x
        coords.y = position.y
        coords
    }

    class Coordinates(var x: Float, var y: Float) {
        def this() = this(0f, 0f)
    }

}