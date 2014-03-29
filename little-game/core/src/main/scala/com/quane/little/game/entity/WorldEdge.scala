package com.quane.little.game.entity

import com.google.common.base.Objects
import com.quane.little.game.engine.InteractionManager
import com.quane.little.game.physics.bodies.StaticBody

class WorldEdge(override val body: StaticBody, manager: InteractionManager)
  extends Entity(body, manager)
  with ImmovableEntity {

  def w: Float = body.w

  def h: Float = body.h

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("x", x)
      .add("y", y)
      .add("w", w)
      .add("h", h)
      .add("id", id)
      .toString

}