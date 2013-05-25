package com.quane.little.game.entity

import com.quane.little.game.InteractionManager
import org.newdawn.slick.Graphics
import com.quane.little.game.view.GameDrawer
import com.quane.little.language.event.LittleEvent
import com.quane.little.game.physics.bodies.EntityBody
import org.eintr.loglady.Logging
import com.quane.little.language.{Operable, Operator}
import scala.util.Random

class Mob(body: EntityBody, manager: InteractionManager)
  extends Entity(body, manager)
  with Operable
  with Logging {

  val operator = new Operator(this)

  // Range of 1-10
  private var _speed = 0

  // Range of 0-360
  private var _direction = Random.nextInt(360)

  override def speed: Int = _speed

  override def speed_=(mph: Int) {
    _speed = mph
  }

  override def direction: Int = _direction

  override def direction_=(degrees: Int) {
    _direction = degrees
  }

  override def isGuy: Boolean = {
    // TODO I hate this
    true
  }

  def heal(amount: Int) {
    log.error("TODO heal by " + amount)
  }

  def touchedBy(other: Entity) {
    // TODO
  }

  def approachedBy(other: Entity) {
    // TODO
  }

  def render(graphics: Graphics) {
    GameDrawer.drawMob(graphics, this)
  }

}