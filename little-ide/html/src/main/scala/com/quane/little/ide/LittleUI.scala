package com.quane.little.ide

import vaadin.scala.UI
import vaadin.scala.server.{ScaladinUIProvider, ScaladinRequest}
import com.vaadin.server.UIProviderEvent
import com.quane.little.ide.presenter.IDEPresenter
import com.quane.little.ide.view.html.IDELayout
import com.vaadin.ui.UI

/** Provides the base user interface to Scaladin.
  */
class LittleUIProvider extends ScaladinUIProvider {

  protected def createScaladinUiInstance(e: UIProviderEvent): UI = new LittleUI

}

/** The little Vaadin user interface.
  */
class LittleUI extends UI(title = "little", theme = "littletheme") {

  override def init(request: ScaladinRequest) = {
    val view = new IDELayout
    new IDEPresenter(view)
    content = view
  }

}