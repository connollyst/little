package com.quane.little.ide

import com.quane.little.ide.view.IDE

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