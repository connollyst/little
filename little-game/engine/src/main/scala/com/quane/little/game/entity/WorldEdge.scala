package com.quane.little.game.entity

import com.quane.little.game.InteractionManager
import com.quane.little.game.view.ShapeDrawer
import com.quane.little.game.physics.bodies.StaticBody

class WorldEdge(override val body: StaticBody, manager: InteractionManager)
  extends Entity(body, manager)
  with ImmovableEntity {

  def w: Float = body.w

  def h: Float = body.h

  override def touchedBy(other: Entity) {
    if (other.isGuy) {
      val mob = other.asInstanceOf[Mob]
      manager.mobContactsImmovableObject(mob)
    }
  }

  override def approachedBy(other: Entity) {
    // TODO
  }

  override def render(shapeDrawer: ShapeDrawer) {
    shapeDrawer.drawWall(this)
  }

}