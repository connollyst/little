package com.quane.little.ide

import scala.swing.event.ButtonClicked
import scala.swing.MenuItem

class ToolBar
        extends scala.swing.MenuBar {
}

object MenuBar {

    val COMPILE_BUTTON_LABEL = "Compile"
    val RUN_BUTTON_LABEL = "Run"
}

class MenuBar
        extends scala.swing.MenuBar {

    val compileButton = new MenuItem(MenuBar.COMPILE_BUTTON_LABEL)
    val runButton = new MenuItem(MenuBar.RUN_BUTTON_LABEL)

    contents += compileButton
    contents += runButton

    listenTo(mouse.clicks, this, compileButton, runButton)
    reactions += {
        case event: ButtonClicked =>
            event.source.text match {
                case MenuBar.COMPILE_BUTTON_LABEL =>
                    IDE.eventBus.post(new DoCompileEvent)
                case MenuBar.RUN_BUTTON_LABEL =>
                    IDE.eventBus.post(new DoRunEvent)
            }
    }

}