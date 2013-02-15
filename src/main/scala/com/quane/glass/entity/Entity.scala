package com.quane.glass.entity

import org.jbox2d.dynamics.Body
import com.quane.glass.core.Guy
import com.quane.glass.engine.Game

abstract class Entity(val body: Body, val game: Game) {

    // TODO body and guy shouldn't be so directly attached
    body.setUserData(this);

    def x: Float = {
        body.getPosition().x
    }

    def y: Float = {
        body.getPosition().y
    }

    def isGuy: Boolean = {
        false
    }

    def touchedBy(other: Entity)

}