package com.quane.glass.game.physics.bodies

import org.jbox2d.dynamics.Body

/** An {@link EntityBody} which never moves.
  *
  */
class StaticBody(physicalBody: Body, x: Float, y: Float, val w: Float, val h: Float)
        extends EntityBody(physicalBody) {

    private val coordinates = new Coordinates(x, y)

    override def coords: Coordinates = coordinates

}