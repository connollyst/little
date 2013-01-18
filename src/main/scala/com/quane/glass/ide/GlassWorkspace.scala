package com.quane.glass.ide

import java.awt.Color
import java.awt.Point

import scala.collection.mutable.ListBuffer
import scala.swing.DesktopPanel
import scala.swing.event.Event
import scala.swing.event.MouseEntered
import scala.swing.event.MouseExited

import org.eintr.loglady.Logging

import com.google.common.eventbus.Subscribe
import com.quane.glass.core.event.EventListener
import com.quane.glass.core.event.GlassEvent
import com.quane.glass.core.language.Expression
import com.quane.glass.ide.language.ExpressionPanelController
import com.quane.glass.ide.language.GlassFrame
import com.quane.glass.ide.language.GlassFrameController
import com.quane.glass.ide.swing.HighlightableComponent

/** The workspace is the area in which programs are created and edited.
  *
  * @author Sean Connolly
  */
class WorkspacePanel
        extends DesktopPanel
        with HighlightableComponent {

    background = Color.white

    val frameControllers = new ListBuffer[GlassFrameController]()

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

    /** Add a frame to the workspace.
      *
      * @param controller
      * 		the controller for the frame
      * @param x
      * 		the x coordinate of the upper left corner of the frame
      * @param y
      * 		the y coordinate of the upper left corner of the frame
      */
    def addEventFrame(panelController: ExpressionPanelController[Expression[Any]], x: Int, y: Int) = {
        val controller = new GlassFrameController(GlassEvent.OnSpawn, new GlassFrame("Sean Is Cool"))
        frameControllers += controller
        controller.view.location = new Point(x, y)
        controller.view.pack
        add(controller.view)
        repaint
    }

    /** Compiles all active frames into their respective Glass code.<br/>
      * TODO Sean.. this doesn't make any sense. What would be the purpose of
      * compiling all _visible_ code?
      */
    def compileAll: List[EventListener] = {
        val eventListeners = new ListBuffer[EventListener]
        frameControllers.foreach(
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
    def dropEvent(event: DropExpressionEvent[WorkspacePanel]) {
        if (overMe) {
            // TODO compensate for offset in header
            val eventX = event.point.x
            val eventY = event.point.y
            val workspaceX = workspace.bounds.x
            val workspaceY = workspace.bounds.y
            val itemX = eventX - workspaceX
            val itemY = eventY - workspaceY
            log.info("Dropping tool: " + event.name)
            val controller = event.dropFunction();
            workspace.addEventFrame(controller, itemX, itemY)
            workspace.unhighlight
        }
    }
}

class WorkspaceCommandEventListener(workspace: WorkspacePanel) {

    // TODO nothing to listen for yet

}