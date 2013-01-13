package com.quane.glass.ide

import scala.swing.MenuItem
import scala.swing.MenuBar
import scala.swing.event.ButtonClicked

object GlassMenuBar {

    val COMPILE_BUTTON_LABEL = "Compile"
    val RUN_BUTTON_LABEL = "Run"
}

class GlassMenuBar
        extends MenuBar {

    val compileButton = new MenuItem(GlassMenuBar.COMPILE_BUTTON_LABEL)
    val runButton = new MenuItem(GlassMenuBar.RUN_BUTTON_LABEL)

    contents += compileButton
    contents += runButton

    listenTo(mouse.clicks, this, compileButton, runButton)
    reactions += {
        case event: ButtonClicked =>
            event.source.text match {
                case GlassMenuBar.COMPILE_BUTTON_LABEL =>
                    GlassIDE.eventBus.post(new DoCompileEvent)
                case GlassMenuBar.RUN_BUTTON_LABEL =>
                    GlassIDE.eventBus.post(new DoRunEvent)
            }
    }

}