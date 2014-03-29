package com.quane.little.game.server

import com.quane.little.game.entity.{WorldEdge, Entity, Mob}
import scala.collection.mutable.ListBuffer
import com.smartfoxserver.v2.mmo.{Vec3D, MMOItemVariable, IMMOItemVariable}
import com.quane.little.Logging

/**
 *
 *
 * @author Sean Connolly
 */
class ItemSerializer
  extends Logging {

  def serialize(entity: Entity): List[IMMOItemVariable] = {
    entity match {
      case mob: Mob => serializeMob(mob)
      case wall: WorldEdge => serializeWorldEdge(wall)
      case _ => serializeEntity(entity)
    }
  }

  def serialize(position: Vec3D): List[IMMOItemVariable] = {
    debug("Serializing position: " + position)
    val variables = new ListBuffer[IMMOItemVariable]
    variables += new MMOItemVariable("x", position.floatX())
    variables += new MMOItemVariable("y", position.floatY())
    variables.toList
  }

  private def serializeMob(mob: Mob): List[IMMOItemVariable] = {
    debug("Serializing Mob MMO item: " + mob)
    val variables = new ListBuffer[IMMOItemVariable]
    variables += new MMOItemVariable("type", "player")
    variables += new MMOItemVariable("uuid", mob.uuid)
    variables += new MMOItemVariable("s", mob.speed)
    variables += new MMOItemVariable("d", mob.direction)
    variables.toList
  }

  private def serializeEntity(entity: Entity): List[IMMOItemVariable] = {
    debug("Serializing Entity MMO item: " + entity)
    val variables = new ListBuffer[IMMOItemVariable]
    variables += new MMOItemVariable("type", "entity")
    variables += new MMOItemVariable("uuid", entity.uuid)
    variables.toList
  }

  private def serializeWorldEdge(wall: WorldEdge): List[IMMOItemVariable] = {
    debug("Serializing Wall MMO item: " + wall)
    val variables = new ListBuffer[IMMOItemVariable]
    variables += new MMOItemVariable("type", "wall")
    variables += new MMOItemVariable("uuid", wall.uuid)
    variables += new MMOItemVariable("w", wall.w.toInt)
    variables += new MMOItemVariable("h", wall.h.toInt)
    variables.toList
  }

}
