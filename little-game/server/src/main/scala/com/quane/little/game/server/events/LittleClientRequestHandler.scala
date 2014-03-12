package com.quane.little.game.server.events

import com.smartfoxserver.v2.extensions.BaseClientRequestHandler
import com.quane.little.game.LittleGameEngine
import com.quane.little.game.server.LittleExtension

trait LittleClientRequestHandler
  extends BaseClientRequestHandler {

  def getGameEngine: LittleGameEngine = getLittleExtension.gameEngine

  def getLittleExtension: LittleExtension =
    getParentExtension match {
      case little: LittleExtension => little
      case _ => throw new IllegalAccessException(
        "Expected parent extension to be LittleExtension."
      )
    }

}
