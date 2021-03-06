package com.quane.little.game.physics.bodies

import org.jbox2d.dynamics.Body

/** An [[com.quane.little.game.physics.bodies.EntityBody]] which never moves.
  *
  * @author Sean Connolly
  */
class StaticBody(physicalBody: Body, x: Float, y: Float, val w: Float, val h: Float)
  extends EntityBody(physicalBody) {

  private val coordinates = new Coordinates(x, y)

  override def coords: Coordinates = coordinates

}