package com.quane.little.ide.view.html

import com.vaadin.ui.{BrowserFrame, Window}
import com.vaadin.server.ExternalResource

/** The window which displays the game in the IDE.
  *
  * @author Sean Connolly
  */
class GameWindow extends Window {

  val game = new ExternalResource("http://localhost/~sean/little/game/little.html")

  center()
  setContent(
    new BrowserFrame("Game", game) {
      setWidth("800px")
      setHeight("600px")
    }
  )

}
