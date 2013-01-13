package com.quane.glass.ide

import scala.swing.MenuItem
import scala.swing.MenuBar
import scala.swing.event.ButtonClicked

class GlassMenuBar
        extends MenuBar {

    val goButton = new MenuItem("Compile")

    contents += goButton

    listenTo(mouse.clicks, this, goButton)
    reactions += {
        case event: ButtonClicked =>
            GlassIDE.eventBus.post(new DoCompileEvent)
    }

    GlassIDE.eventBus.register(new MenuBarListener)

}