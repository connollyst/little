package com.quane.little.ide.events

import com.quane.little.ide.layout.IDE
import com.google.common.eventbus.Subscribe

/** A listener for [[com.quane.little.ide.events.MenuBarEvents]];
  * events fired by the [[com.quane.little.ide.controls.MenuBar]].
  *
  * @author Sean Connolly
  */
class MenuBarEventListener(ide: IDE) {

  @Subscribe
  def compileEvent(event: MenuBarDoCompileEvent) {
    ide.compile()
  }

  @Subscribe
  def runEvent(event: MenuBarDoRunEvent) {
    ide.run()
  }

}
