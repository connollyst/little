package com.quane.little.ide.view.html

import com.vaadin.ui._
import com.quane.little.ide.view.GamespaceView
import com.vaadin.server.Sizeable
import com.quane.little.language.event.EventListener
import com.quane.little.data.model.RecordId
import com.quane.vaadin.scala.VaadinMixin
import com.porotype.iconfont.FontAwesome.{IconVariant, Icon}
import com.vaadin.ui.Button.{ClickEvent, ClickListener}

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
  val listeners = new GamespaceListenerLayout(this)

  setFirstComponent(game)
  setSecondComponent(listeners)

  override def createGameListener(listener: EventListener, listenerId: RecordId) =
    listeners.addGameListener(listener, listenerId)

}

class GamespaceViewer
  extends CssLayout {

  setStyleName(GamespaceLayout.StyleView)

}

class GamespaceListenerLayout(view: GamespaceLayout)
  extends VerticalLayout
  with VaadinMixin {

  setSpacing(true)
  setStyleName(GamespaceLayout.StyleListeners)

  def addGameListener(listener: EventListener, listenerId: RecordId) = {
    val label = new Label(listener.event + ": " + listener.function.name)
    val button = new NativeButton(Icon.edit.variant(IconVariant.SIZE_LARGE),
      new ClickListener() {
        override def buttonClick(event: ClickEvent) = {
          view.presenter.openGameListener(listenerId)
        }
      }
    )
    button.setHtmlContentAllowed(true)
    val wrapper = new HorizontalLayout
    wrapper.addComponent(label)
    wrapper.addComponent(button)
    wrapper.setSpacing(true)
    wrapper.setStyleName(ExpressionLayout.Style)
    wrapper.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT)
    add(wrapper)
  }

}