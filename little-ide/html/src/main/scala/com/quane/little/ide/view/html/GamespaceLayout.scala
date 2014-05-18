package com.quane.little.ide.view.html

import com.vaadin.ui._
import com.quane.little.ide.view.GamespaceView
import com.vaadin.server.{ExternalResource, Sizeable}
import com.quane.little.language.event.EventListener
import com.quane.little.data.model.RecordId
import com.quane.vaadin.scala.VaadinMixin
import com.porotype.iconfont.FontAwesome.{IconVariant, Icon}
import com.vaadin.ui.Button.{ClickEvent, ClickListener}

object GamespaceLayout {
  val Style = "l-gamespace"
  val StyleView = Style + "-viewer"
  val StyleListeners = Style + "-listeners"
  val GameURL = new ExternalResource("http://localhost/~sean/little/game/little/little.html?ide_id=debug")
}

/** Layout containing the core game elements: a display of the game itself and
  * the tools for editing the [[EventListener]] list.
  *
  * @author Sean connolly
  */
class GamespaceLayout
  extends VerticalSplitPanel
  with GamespaceView {

  setLocked(true)
  setPrimaryStyleName(GamespaceLayout.Style)
  setSplitPosition(33, Sizeable.Unit.PERCENTAGE)

  val game = new GamespaceViewer()
  val listeners = new GamespaceListenerLayout(this)

  setFirstComponent(game)
  setSecondComponent(listeners)

  override def createGameListener(listener: EventListener, listenerId: RecordId) =
    listeners.addGameListener(listener, listenerId)

}

/** Browser frame displaying the game.
  *
  * @author Sean connolly
  */
class GamespaceViewer
  extends BrowserFrame("", GamespaceLayout.GameURL) {

  setSizeFull()
  setStyleName(GamespaceLayout.StyleView)

}

/** Layout containing this user's [[com.quane.little.language.event.EventListener]]
  * list and controls for creating and editing them.
  *
  * @param view the root gamespace layout
  *
  * @author Sean connolly
  */
class GamespaceListenerLayout(view: GamespaceLayout)
  extends VerticalLayout
  with VaadinMixin {

  setSpacing(true)
  setStyleName(GamespaceLayout.StyleListeners)

  add(new NativeButton("New Listener",
    new ClickListener {
      override def buttonClick(event: ClickEvent) =
        view.presenter.newGameListener()
    })
  )
  add(new NativeButton("New Function",
    new ClickListener {
      override def buttonClick(event: ClickEvent) =
        view.presenter.newFunction()
    })
  )

  def addGameListener(listener: EventListener, listenerId: RecordId) = {
    val label = new Label(listener.event.toString)
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
    wrapper.setSizeFull()
    wrapper.setSpacing(true)
    wrapper.setStyleName(ExpressionLayout.Style)
    wrapper.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT)
    wrapper.setExpandRatio(label, 1f)
    add(wrapper)
  }

}