package com.quane.little.ide

import vaadin.scala.UI
import vaadin.scala.server.{ScaladinUIProvider, ScaladinRequest}
import com.vaadin.server.UIProviderEvent
import com.quane.little.ide.view.html.IDE

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