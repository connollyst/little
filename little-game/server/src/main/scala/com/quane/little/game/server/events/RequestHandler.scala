package com.quane.little.game.server.events

import com.quane.little.game.server.LittleExtension
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler

abstract class RequestHandler
  extends BaseClientRequestHandler {

  protected def extension: LittleExtension =
    getParentExtension match {
      case little: LittleExtension => little
      case _ => throw new IllegalAccessException("Expected LittleExtension.")
    }

}
