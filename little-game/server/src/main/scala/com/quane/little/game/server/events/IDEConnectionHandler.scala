package com.quane.little.game.server.events

import com.smartfoxserver.v2.entities.User
import com.smartfoxserver.v2.entities.data.{SFSDataType, SFSObject, ISFSObject}
import com.smartfoxserver.v2.mmo.Vec3D
import com.escalatesoft.subcut.inject.{Injectable, BindingModule}
import com.quane.little.data.service.UserService
import com.quane.little.game.server.events.IDEConnectionHandler._

object IDEConnectionHandler {
  val IdeIdKey = "ide_id"
}

class IDEConnectionHandler(implicit val bindingModule: BindingModule)
  extends RequestHandler
  with Injectable {

  private val userService = inject[UserService]

  override def handleClientRequest(user: User, data: ISFSObject): Unit = {
    trace("Authenticating IDE for '" + user + "' with keys: " + data.getKeys)
    val value = data.get(IdeIdKey)
    value.getTypeId match {
      case SFSDataType.UTF_STRING =>
        value.getObject.toString match {
          case "debug" => setDebugPosition(user)
          case ideId => setUserPosition(user, ideId)
        }
      case _ => throw new IllegalArgumentException(
        "Expected " + IdeIdKey + " as String, but was " + value.getTypeId
      )
    }
  }

  private def setUserPosition(user: User, ideId: String): Unit = {
    trace("AUTHENTICATING '" + ideId + "'..")
    // TODO this is not how we want to authorize IDEs
    if (!userService.exists(ideId)) {
      // TODO put ideId somewhere
      val little = extension
      val room = little.getParentRoom
      val mmo = little.getMMOApi
      val position = new Vec3D(50.0f, 50.0f, 0)
      mmo.setUserPosition(user, position, room)
      sendValidResponse(user, valid = true)
    } else {
      throw new IllegalArgumentException("No user with username " + ideId)
    }
  }

  private def setDebugPosition(user: User): Unit = {
    trace("AUTHENTICATING DEBUGGER")
    val little = extension
    val room = little.getParentRoom
    val mmo = little.getMMOApi
    val position = new Vec3D(50.0f, 50.0f, 0)
    mmo.setUserPosition(user, position, room)
    sendValidResponse(user, valid = true)
  }

  private def sendValidResponse(user: User, valid: Boolean): Unit = {
    val data = new SFSObject()
    data.putBool("valid", valid)
    extension.send(LittleEvents.IDE_AUTH, data, user)
  }

}
