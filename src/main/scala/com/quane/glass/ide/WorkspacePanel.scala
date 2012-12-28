package com.quane.glass.ide

import java.awt.Color
import java.awt.Dimension
import java.awt.Point
import scala.swing.Button
import scala.swing.event.Event
import scala.swing.event.MouseEntered
import scala.swing.event.MouseExited
import javax.swing.BorderFactory
import scala.swing.Panel
import scala.swing.BoxPanel
import scala.swing.Orientation
import scala.swing.Label
import javax.swing.JDesktopPane
import scala.swing.DesktopPanel
import com.google.common.eventbus.Subscribe

/** The workspace is the area in which programs are created and edited.
  *
  * @author Sean Connolly
  */
class WorkspacePanel
        extends DesktopPanel
        with HighlightableComponent {

    border = GlassIDE.DEFAULT_BORDER
    background = Color.white

    // Listen for the mouse entering and exiting the workspace
    listenTo(mouse.moves)
    reactions += {
        case event: MouseEntered =>
            GlassIDE.eventBus.post(new WorkspaceEnteredEvent)
        case event: MouseExited =>
            GlassIDE.eventBus.post(new WorkspaceExitedEvent)
    }

    // Listen for drag & drop events on the event bus
    GlassIDE.eventBus.register(new WorkspaceDragAndDropEventListener(this))

    

    /** Create a new panel on the workspace.
      *
      * @param title
      * 		the title to give the panel
      * @param x
      * 		the x coordinate of the upper left corner of the panel
      * @param y
      * 		the y coordinate of the upper left corner of the panel
      */
    def createEventPanel(title: String, x: Int, y: Int) {
        val frame = new ProgramPanel(title)
        frame.location = new Point(x, y)
        frame.pack
        add(frame)
        repaint
    }

}

class WorkspaceEnteredEvent extends Event

class WorkspaceExitedEvent extends Event

class WorkspaceDragAndDropEventListener(workspacePanel: WorkspacePanel) {

    var overMe = false

    @Subscribe
    def dragOverEvent(event: DragOverWorkspaceEvent) {
        overMe = true
        workspacePanel.highlight
    }

    @Subscribe
    def dragOutEvent(event: DragOutWorkspaceEvent) {
        overMe = false
        workspacePanel.unhighlight
    }

    @Subscribe
    def dropEvent(event: DragDropEvent[WorkspacePanel]) {
        if (overMe) {
            // TODO compensate for offset in header
            val eventX = event.point.x
            val eventY = event.point.y
            val workspaceX = workspacePanel.bounds.x
            val workspaceY = workspacePanel.bounds.y
            val itemX = eventX - workspaceX
            val itemY = eventY - workspaceY
            println("Dropping tool: " + event.name)
            workspacePanel.createEventPanel(event.name, itemX, itemY)
            workspacePanel.unhighlight
        }
    }
}