package com.quane.glass.game.entity

import org.jbox2d.dynamics.Body
import com.quane.glass.game.Game
import org.newdawn.slick.Graphics

abstract class Entity(val body: Body, val game: Game) {

    val isRemoved = false

    // TODO body and guy shouldn't be so directly attached
    body.setUserData(this)

    def x: Float = {
        body.getPosition().x
    }

    def y: Float = {
        body.getPosition().y
    }

    def isGuy: Boolean = {
        false
    }

    def render(graphics: Graphics)
    
    def touchedBy(other: Entity)

}