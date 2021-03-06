package com.quane.little.ide.view.html

import com.porotype.iconfont.FontAwesome.{Icon, IconVariant}
import com.quane.little.data.model.ListenerId
import com.quane.little.ide.view.GamespaceView
import com.quane.little.language.event.EventListener
import com.quane.vaadin.scala.VaadinMixin
import com.vaadin.server.{ExternalResource, Sizeable}
import com.vaadin.shared.ui.window.WindowMode
import com.vaadin.ui.Button.{ClickEvent, ClickListener}
import com.vaadin.ui.Window.{CloseEvent, CloseListener}
import com.vaadin.ui._

object GamespaceLayout {
  val Style = "l-gamespace"
  val StyleView = Style + "-viewer"
  val StyleListeners = Style + "-listeners"
  // TODO URL should come from presenter
  val GameURL = new ExternalResource("http://localhost/~sean/little/game/little2/little.html?username=connollyst")
}

/** Layout containing the core game elements: a display of the game itself and
  * the tools for editing the [[com.quane.little.language.event.EventListener]]
  * list.
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

  override def createGameListener(listener: EventListener, id: ListenerId) =
    listeners.addGameListener(listener, id)

}

/** Browser frame displaying the game.
  *
  * @author Sean connolly
  */
class GamespaceViewer
  extends VerticalLayout
  with VaadinMixin {

  private val browserWrapper = add(new CssLayout with VaadinMixin)
  private val browser = browserWrapper.add(new BrowserFrame("", GamespaceLayout.GameURL))
  private val controls = add(new HorizontalLayout)

  setSizeFull()
  setExpandRatio(browserWrapper, 1)
  setStyleName(GamespaceLayout.StyleView)

  browserWrapper.setSizeFull()
  browser.setSizeFull()
  controls.setWidth(100, Sizeable.Unit.PERCENTAGE)
  controls.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT)
  controls.addComponent(new FullscreenButton)

  private class FullscreenButton extends NativeButton {
    setCaption(Icon.fullscreen.variant(IconVariant.SIZE_LARGE))
    setHtmlContentAllowed(true)
    addClickListener(new ClickListener {
      override def buttonClick(event: ClickEvent) = {
        val window = new Window("little", browser)
        window.setWidth(80, Sizeable.Unit.PERCENTAGE)
        window.setHeight(80, Sizeable.Unit.PERCENTAGE)
        window.setWindowMode(WindowMode.MAXIMIZED)
        window.center()
        window.addCloseListener(new CloseListener {
          override def windowClose(e: CloseEvent) = {
            browserWrapper.add(browser)
          }
        })
        getUI.addWindow(window)
      }
    })
  }

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

  def addGameListener(listener: EventListener, id: ListenerId) = {
    val label = new Label(listener.event.toString)
    val button = new NativeButton(Icon.edit.variant(IconVariant.SIZE_LARGE),
      new ClickListener() {
        override def buttonClick(event: ClickEvent) = {
          view.presenter.openGameListener(id)
        }
      }
    )
    button.setHtmlContentAllowed(true)
    val wrapper = new HorizontalLayout
    wrapper.addComponent(label)
    wrapper.addComponent(button)
    wrapper.setSizeFull()
    wrapper.setSpacing(true)
    wrapper.setStyleName(CodeLayout.Style)
    wrapper.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT)
    wrapper.setExpandRatio(label, 1f)
    add(wrapper)
  }

}