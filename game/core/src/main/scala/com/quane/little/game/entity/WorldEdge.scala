package com.quane.little.game.entity

import com.quane.little.game.InteractionManager
import com.quane.little.game.view.GameDrawer
import com.quane.little.game.physics.bodies.StaticBody
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class WorldEdge(override val body: StaticBody, manager: InteractionManager)
  extends Entity(body, manager)
  with ImmovableEntity {

  def touchedBy(other: Entity) {
    if (other isGuy) {
      val mob = other.asInstanceOf[Mob]
      manager.mobContactsImmovableObject(mob)
    }
  }

  def approachedBy(other: Entity) {
    // TODO
  }

  def render(graphics: SpriteBatch) {
    GameDrawer.drawWall(graphics, this)
  }

}