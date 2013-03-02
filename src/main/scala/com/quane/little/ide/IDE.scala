package com.quane.little.ide

import scala.swing.BorderPanel
import scala.swing.Dimension
import scala.swing.MainFrame
import scala.swing.SimpleSwingApplication

import org.eintr.loglady.Logging

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe

object IDE extends SimpleSwingApplication {

    // Set the window title in Mac 
    System.setProperty("com.apple.mrj.application.apple.menu.about.name", "little");

    // TODO replace static event bus with dependency injection
    val eventBus = new EventBus

    def top = new MainFrame {
        title = "little IDE"
        minimumSize = new Dimension(1024, 768)
        contents = new BasePanel
        menuBar = new MenuBar
    }

}

class BasePanel
        extends BorderPanel {

    val toolkitPanel = new ToolkitPanel
    val workspace = new WorkspaceController(new WorkspacePanel)
    val toolbar = new ToolBar

    layout(toolkitPanel) = BorderPanel.Position.West
    layout(workspace.view) = BorderPanel.Position.Center
    layout(toolbar) = BorderPanel.Position.South

    IDE.eventBus.register(new MenuBarListener(this))
    IDE.eventBus.register(new DragAndDropEventListener)

}

class MenuBarListener(ide: BasePanel)
        extends Logging {

    @Subscribe
    def compileEvent(event: DoCompileEvent) {
        log.info("Compiling..")
        ide.workspace.compileAllFunctions
        ide.workspace.compileAllListeners
    }

    @Subscribe
    def runEvent(event: DoRunEvent) {
        log.info("Compiling..")
        val eventListeners = ide.workspace.compileAllListeners
        log.info("Running..");
        new GlassGameFrame().run(eventListeners)
    }

}

