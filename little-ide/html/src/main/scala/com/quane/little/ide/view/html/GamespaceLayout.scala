package com.quane.little.ide.view.html

import com.vaadin.ui._
import com.quane.little.ide.view.GamespaceView
import com.vaadin.server.Sizeable
import com.quane.little.language.event.EventListener
import com.quane.little.data.model.RecordId
import com.quane.vaadin.scala.VaadinMixin

object GamespaceLayout {
  val Style = "l-gamespace"
  val StyleView = Style + "-viewer"
  val StyleListeners = Style + "-listeners"
}

class GamespaceLayout
  extends VerticalSplitPanel
  with GamespaceView {

  setLocked(true)
  setPrimaryStyleName(GamespaceLayout.Style)
  setSplitPosition(33, Sizeable.Unit.PERCENTAGE)

  val game = new CssLayout()
  val listeners = new GamespaceListenerLayout

  setFirstComponent(game)
  setSecondComponent(listeners)

  override def createGameListener(listener: EventListener, listenerId: RecordId) =
    listeners.addGameListener(listener, listenerId)

}

class GamespaceViewer
  extends CssLayout {

  setStyleName(GamespaceLayout.StyleView)

}

class GamespaceListenerLayout
  extends VerticalLayout
  with VaadinMixin {

  setSpacing(true)
  setStyleName(GamespaceLayout.StyleListeners)

  def addGameListener(listener: EventListener, listenerId: RecordId) = {
    val label = new Label(listener.event + ": " + listener.function.name)
    label.setStyleName(ExpressionLayout.Style)
    add(label)
  }

}