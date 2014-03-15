package com.quane.little.game.server.events

import com.smartfoxserver.v2.extensions.BaseClientRequestHandler
import com.quane.little.game.LittleGameEngine
import com.quane.little.game.server.LittleExtension
import com.smartfoxserver.v2.entities.User
import com.quane.little.game.entity.{Entity, Mob}
import com.smartfoxserver.v2.entities.data.{SFSObject, ISFSObject}

abstract class LittleClientRequestHandler
  extends BaseClientRequestHandler {

  def getGameEngine: LittleGameEngine = getLittleExtension.gameEngine

  def getLittleExtension: LittleExtension =
    getParentExtension match {
      case little: LittleExtension => little
      case _ => throw new IllegalAccessException(
        "Expected parent extension to be LittleExtension."
      )
    }

  private[events] def updateState(user: User) = {
    getGameEngine.players.keys foreach {
      uuid =>
        getParentExtension.trace("Updating " + uuid + " to " + user.getPlayerId)
        val data = toSFSObject(getGameEngine.players(uuid))
        send("UpdatePlayer", data, user)
    }
  }

  private[events] def sendState(user: User) = {
    getGameEngine.players.keys foreach {
      uuid =>
        getParentExtension.trace("Sending " + uuid + " to " + user.getPlayerId)
        val data = toSFSObject(getGameEngine.players(uuid))
        send("SetPlayer", data, user)
    }
    getGameEngine.entities.keys foreach {
      uuid =>
        getParentExtension.trace("Sending " + uuid + " to " + user.getPlayerId)
        val data = toSFSObject(getGameEngine.entities(uuid))
        send("SetItem", data, user)
    }
  }

  private def toSFSObject(mob: Mob): ISFSObject = {
    val data = new SFSObject()
    data.putUtfString("id", mob.uuid.toString)
    data.putFloat("x", mob.x)
    data.putFloat("y", mob.y)
    data.putInt("s", mob.speed)
    data.putInt("d", mob.direction)
    data
  }

  private def toSFSObject(mob: Entity): ISFSObject = {
    val data = new SFSObject()
    data.putUtfString("id", mob.uuid.toString)
    data.putFloat("x", mob.x)
    data.putFloat("y", mob.y)
    data
  }

}
