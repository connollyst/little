package com.quane.little.game.server

import com.quane.little.game.entity.{WorldEdge, Entity, Mob}
import com.smartfoxserver.v2.mmo.{Vec3D, MMOItemVariable, IMMOItemVariable}
import scala.collection.mutable.ListBuffer

/** Serializes [[Entity]] objects as SmartFox [[IMMOItemVariable]] objects to be
  * sent to the client.
  *
  * @author Sean Connolly
  */
class EntitySerializer {

  def serialize(entity: Entity): List[IMMOItemVariable] = {
    entity match {
      case mob: Mob => serializeMob(mob)
      case wall: WorldEdge => serializeWorldEdge(wall)
      case _ => serializeEntity(entity)
    }
  }

  def serialize(position: Vec3D): List[IMMOItemVariable] = {
    val variables = new ListBuffer[IMMOItemVariable]
    variables += new MMOItemVariable("x", position.floatX)
    variables += new MMOItemVariable("y", position.floatY)
    variables.toList
  }

  private def serializeMob(mob: Mob): List[IMMOItemVariable] = {
    val variables = new ListBuffer[IMMOItemVariable]
    variables += new MMOItemVariable("type", "player")
    variables += new MMOItemVariable("id", mob.id)
    variables += new MMOItemVariable("s", mob.speed)
    variables += new MMOItemVariable("d", mob.direction)
    variables.toList
  }

  private def serializeEntity(entity: Entity): List[IMMOItemVariable] = {
    val variables = new ListBuffer[IMMOItemVariable]
    variables += new MMOItemVariable("type", "entity")
    variables += new MMOItemVariable("id", entity.id)
    variables.toList
  }

  private def serializeWorldEdge(wall: WorldEdge): List[IMMOItemVariable] = {
    val variables = new ListBuffer[IMMOItemVariable]
    variables += new MMOItemVariable("type", "wall")
    variables += new MMOItemVariable("id", wall.id)
    variables += new MMOItemVariable("w", wall.w.toInt)
    variables += new MMOItemVariable("h", wall.h.toInt)
    variables.toList
  }

}
