package com.quane.little.game.entity

import com.google.common.base.Objects
import com.quane.little.game.engine.InteractionManager
import com.quane.little.game.physics.bodies.StaticBody

/** An inanimate (`speed=0` &amp; `direction=0`) [[com.quane.little.game.entity.Entity]]
  * used to mark the boundaries of the game world.
  *
  * @param body the entity's physical body
  * @param manager the game's interaction manager
  *
  * @author Sean Connolly
  */
class WorldEdge(override val body: StaticBody, manager: InteractionManager)
  extends Entity(body, manager)
  with ImmovableEntity {

  def w: Float = body.w

  def h: Float = body.h

  override def speed = 0

  override def speed_=(mph: Int) = Unit

  override def direction = 0

  override def direction_=(degrees: Int) = Unit

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("x", x)
      .add("y", y)
      .add("w", w)
      .add("h", h)
      .add("id", id)
      .toString

}