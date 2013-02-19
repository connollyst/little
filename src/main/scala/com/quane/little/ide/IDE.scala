package com.quane.little.ide

import scala.swing.BorderPanel
import scala.swing.Dimension
import scala.swing.MainFrame
import scala.swing.SimpleSwingApplication

import org.eintr.loglady.Logging

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe

// TODO
// X add toolkit on left
// X add workspace on right
// X add elements to toolkit
// X d&d elements from toolkit to workspace
// X d&d elements into programs
// X compile programs to expressions
// X add game into ide
// X add menu to run game

// V1:
// OnSpawn - Move forward
// OnContact - Turn Randomly
// OnCloseToFood - Go Toward Food
// OnGotFood - Stop
// V2:
// Backed by GUI
// V3:
// Other objects.. can bounce off anything
// V4:
// Remove Walls.
// Scrolling - limited distance from dude
// Large World - not procedural ATM

// - game always running
// - push changes to game on 'Run'
// - game follows guy 




// ~ add support for other events
// - add support for multiple guys
// - add support for obstacles
// - add speech bubbles
// - add food
// - save and load programs
// - add better IDE controls
// - add better memory handling (types, limit, visualization, etc.)
// - code should be time controlled (eg one Expression per frame)
// - add camera scrolling, tracking, etc.
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
        ide.workspace.compileAll
    }

    @Subscribe
    def runEvent(event: DoRunEvent) {
        log.info("Compiling..")
        val eventListeners = ide.workspace.compileAll
        log.info("Running..");
        new GlassGameFrame().run(eventListeners)
    }

}

