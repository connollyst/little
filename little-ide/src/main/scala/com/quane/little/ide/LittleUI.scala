package com.quane.little.ide

import vaadin.scala.UI
import com.quane.little.web.view.IDE
import vaadin.scala.server.ScaladinRequest
import com.vaadin.server.UIProviderEvent
import vaadin.scala.server.ScaladinUIProvider

/**
 * The base user interface for the little IDE.
 *
 * @author Sean Connolly
 */
class LittleUI extends UI(title = "little", theme = "littletheme") {

  override def init(request: ScaladinRequest) = content = new IDE

}

class LittleUIProvider extends ScaladinUIProvider {

  protected def createScaladinUiInstance(e: UIProviderEvent): UI = new LittleUI

}