package com.quane.little.ide.view.html

import com.vaadin.ui._
import com.quane.little.ide.view.GamespaceView
import com.vaadin.server.Sizeable

object GamespaceLayout {
  val Style = "l-gamespace"
  val StyleView = Style + "-viewer"
  val StyleListeners = Style + "-listeners"
}

class GamespaceLayout
  extends VerticalSplitPanel
  with GamespaceView {

  setLocked(true)
  setStyleName(GamespaceLayout.Style)
  setSplitPosition(33, Sizeable.Unit.PERCENTAGE)

  val game = new CssLayout()
  val listeners = new GamespaceAccordion

  setFirstComponent(game)
  setSecondComponent(listeners)

}

class GamespaceViewer
  extends CssLayout {

  setStyleName(GamespaceLayout.StyleView)

}

class GamespaceAccordion
  extends Accordion {

  setStyleName(GamespaceLayout.StyleListeners)

}