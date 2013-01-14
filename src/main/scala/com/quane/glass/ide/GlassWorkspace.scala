package com.quane.glass.ide

import java.awt.Color
import java.awt.Point
import scala.collection.mutable.ListBuffer
import scala.swing.DesktopPanel
import scala.swing.event.Event
import scala.swing.event.MouseEntered
import scala.swing.event.MouseExited
import com.google.common.eventbus.Subscribe
import com.quane.glass.core.event.EventListener
import com.quane.glass.core.event.GlassEvent
import com.quane.glass.core.event.GlassEvent.OnSpawn
import com.quane.glass.ide.language.GlassEventFrame
import com.quane.glass.ide.language.GlassEventFrameController
import com.quane.glass.ide.swing.HighlightableComponent
import org.eintr.loglady.Logging

/** The workspace is the area in which programs are created and edited.
  *
  * @author Sean Connolly
  */
class WorkspacePanel
        extends DesktopPanel
        with HighlightableComponent {

    background = Color.white

    val eventFrameControllers = new ListBuffer[GlassEventFrameController]()

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
    GlassIDE.eventBus.register(new WorkspaceCommandEventListener(this))

    /** Create a new panel on the workspace.
      *
      * @param title
      * 		the title to give the panel
      * @param x
      * 		the x coordinate of the upper left corner of the panel
      * @param y
      * 		the y coordinate of the upper left corner of the panel
      */
    def createEventPanel(event: GlassEvent, x: Int, y: Int) = {
        val frame = new GlassEventFrame(event)
        eventFrameControllers += new GlassEventFrameController(frame)
        frame.location = new Point(x, y)
        frame.pack
        add(frame)
        repaint
    }

    /** Compiles all active frames into their respective Glass code.<br/>
      * TODO Sean.. this doesn't make any sense. What would be the purpose of
      * compiling all _visible_ code?
      */
    def compileAll: List[EventListener] = {
        val eventListeners = new ListBuffer[EventListener]
        eventFrameControllers.foreach(
            controller => {
                eventListeners += controller.compile
            }
        )
        eventListeners toList
    }

}

class WorkspaceEnteredEvent extends Event

class WorkspaceExitedEvent extends Event

class WorkspaceDragAndDropEventListener(workspace: WorkspacePanel)
        extends Logging {

    var overMe = false

    @Subscribe
    def dragOverEvent(event: DragOverWorkspaceEvent) {
        overMe = true
        workspace.highlight
    }

    @Subscribe
    def dragOutEvent(event: DragOutWorkspaceEvent) {
        overMe = false
        workspace.unhighlight
    }

    @Subscribe
    def dropEvent(event: DragDropEvent[WorkspacePanel]) {
        if (overMe) {
            // TODO compensate for offset in header
            val eventX = event.point.x
            val eventY = event.point.y
            val workspaceX = workspace.bounds.x
            val workspaceY = workspace.bounds.y
            val itemX = eventX - workspaceX
            val itemY = eventY - workspaceY
            log.info("Dropping tool: " + event.name)
            workspace.createEventPanel(OnSpawn, itemX, itemY) // TODO get type from event
            workspace.unhighlight
        }
    }
}

class WorkspaceCommandEventListener(workspace: WorkspacePanel) {

    // TODO nothing to listen for yet

}