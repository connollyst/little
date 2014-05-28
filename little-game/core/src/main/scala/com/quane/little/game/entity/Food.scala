package com.quane.little.game.entity

import com.quane.little.game.engine.InteractionManager
import com.quane.little.game.physics.bodies.EntityBody

/** An inanimate (`speed=0` &amp; `direction=0`) [[com.quane.little.game.entity.Entity]]
  * which gives `health` when consumed.
  *
  * @param body the entity's physical body
  * @param manager the game's interaction manager
  * @param health the amount of health given when consumed
  *
  * @author Sean Connolly
  */
class Food(body: EntityBody, manager: InteractionManager, val health: Int)
  extends Entity(body, manager) {

  override def speed = 0

  override def speed_=(mph: Int) = Unit

  override def direction = 0

  override def direction_=(degrees: Int) = Unit

}