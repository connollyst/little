package com.quane.little.ide

import com.vaadin.server.VaadinRequest
import com.quane.little.ide.presenter.IDEPresenter
import com.quane.little.ide.view.html.IDELayout
import com.vaadin.ui.UI
import com.vaadin.annotations.{Theme, Title}

/** The little Vaadin user interface.
  */
@Title("little")
@Theme("littletheme")
class LittleUI extends UI {

  override def init(request: VaadinRequest) = {
    val view = new IDELayout
    new IDEPresenter(view)
    setContent(view)
  }

}