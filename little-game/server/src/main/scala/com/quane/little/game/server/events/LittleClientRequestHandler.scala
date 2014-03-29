package com.quane.little.game.server.events

import com.smartfoxserver.v2.extensions.BaseClientRequestHandler
import com.quane.little.game.server.LittleExtension
import com.quane.little.game.entity.Mob
import com.smartfoxserver.v2.entities.data.ISFSObject

abstract class LittleClientRequestHandler
  extends BaseClientRequestHandler {

  def getLittleExtension: LittleExtension =
    getParentExtension match {
      case little: LittleExtension => little
      case _ => throw new IllegalAccessException(
        "Expected parent extension to be LittleExtension."
      )
    }

}
