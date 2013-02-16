package com.quane.glass.game.physics.bodies

import org.jbox2d.dynamics.Body
import com.quane.glass.game.entity.Entity
import org.jbox2d.common.Vec2

class EntityBody(val physicalBody: Body) {

    def attach(entity: Entity) {
        physicalBody.setUserData(entity)
    }

    def position: Vec2 = {
        physicalBody.getPosition();
    }

}