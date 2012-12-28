package com.quane.glass.ide

import scala.swing.BorderPanel
import scala.swing.Dimension
import scala.swing.MainFrame
import scala.swing.SimpleSwingApplication
import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import scala.swing.event.Event
import javax.swing.BorderFactory
import java.awt.Color

// TODO
// X add toolkit on left
// X add workspace on right
// X add elements to toolkit
// X d&d elements from toolkit to workspace
// X d&d elements into programs
// - translate programs to Expressions
// - encorporate game into application
// - add menu to run game
// - serialize and deserialize programs
object GlassIDE extends SimpleSwingApplication {

    // Set the application title in Mac 
    System.setProperty("com.apple.mrj.application.apple.menu.about.name", "GlassIDE");

    val DEFAULT_BORDER = BorderFactory.createLineBorder(Color.black)
    val HIGHLIGHTED_BORDER = BorderFactory.createLineBorder(Color.yellow)

    // TODO replace static event bus with dependency injection
    val eventBus = new EventBus

    def top = new MainFrame {
        title = "Glass"
        minimumSize = new Dimension(800, 600)
        contents = new BasePanel
    }

}

class BasePanel extends BorderPanel {

    val toolkitPanel = new ToolkitPanel
    val workspacePanel = new WorkspacePanel

    layout(toolkitPanel) = BorderPanel.Position.West
    layout(workspacePanel) = BorderPanel.Position.Center

    GlassIDE.eventBus.register(new DragAndDropEventListener)

}

class DragAndDropEventListener {

    var dragging = None: Option[String]

    @Subscribe
    def dragEvent(event: ToolDraggedEvent) {
        dragging = Option(event.tool);
    }

    @Subscribe
    def dropEvent(event: ToolDroppedEvent) {
        dragging = None;
        GlassIDE.eventBus.post(new DragDropEvent(event.tool, event.toolType, event.point))
    }

    @Subscribe
    def enterRecipientEvent(event: WorkspaceEnteredEvent) {
        if (dragging isDefined) {
            GlassIDE.eventBus.post(new DragOverWorkspaceEvent(dragging.get))
        }
    }

    @Subscribe
    def exitRecipientEvent(event: WorkspaceExitedEvent) {
        if (dragging isDefined) {
            GlassIDE.eventBus.post(new DragOutWorkspaceEvent(dragging.get))
        }
    }

    @Subscribe
    def enterRecipientEvent(event: ProgramEnteredEvent) {
        if (dragging isDefined) {
            GlassIDE.eventBus.post(new DragOverProgramEvent(dragging.get))
        }
    }

    @Subscribe
    def exitRecipientEvent(event: ProgramExitedEvent) {
        if (dragging isDefined) {
            GlassIDE.eventBus.post(new DragOutProgramEvent(dragging.get))
        }
    }

}