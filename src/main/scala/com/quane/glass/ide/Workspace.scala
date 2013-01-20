package com.quane.glass.ide

import java.awt.Color
import java.awt.Point
import scala.collection.mutable.ListBuffer
import scala.swing.DesktopPanel
import scala.swing.event.MouseEntered
import scala.swing.event.MouseExited
import org.eintr.loglady.Logging
import com.quane.glass.core.event.EventListener
import com.quane.glass.core.language.Expression
import com.quane.glass.ide.language.ExpressionPanelController
import com.quane.glass.ide.language.WorkspaceFrame
import com.quane.glass.ide.language.WorkspaceFrameController
import com.quane.glass.ide.swing.HighlightableComponent
import com.quane.glass.ide.language.FunctionPanelController
import com.quane.glass.ide.language.ListenerPanelController

/** The workspace is the area in which programs are created and edited.
  *
  * @author Sean Connolly
  */
class WorkspacePanel
        extends DesktopPanel
        with DragAndDropTarget
        with HighlightableComponent
        with Logging {

    background = Color.white

    val frameControllers = new ListBuffer[WorkspaceFrameController]()

    /** Returns true/false if the specified item can/cannot be dropped here,
      * respectively.
      *
      * @param item
      * 		the drag and drop item
      */
    def accepts(item: DragAndDropItem): Boolean = {
        return item match {
            case EventToolType => true
            case _             => false
        }
    }

    // Listen for the mouse entering and exiting the workspace
    listenTo(mouse.moves)
    reactions += {
        case event: MouseEntered =>
            log.info("WorkspacePanel.MouseEntered")
            IDE.eventBus.post(event)
        case event: MouseExited =>
            log.info("WorkspacePanel.MouseExited")
            IDE.eventBus.post(event)
        case event: DragOverEvent =>
            log.info("WorkspacePanel.DragOverEvent")
            highlight
        case event: DragOutEvent =>
            log.info("WorkspacePanel.DragOutEvent")
            unhighlight
        case event: DropExpressionEvent =>
            log.info("WorkspacePanel.DropExpressionEvent")
            unhighlight
            val controller = event.dropFunction()
            controller match {
                case listenerController: ListenerPanelController =>
                    // TODO compensate for offset in header
                    val eventX = event.point.x
                    val eventY = event.point.y
                    val workspaceX = bounds.x
                    val workspaceY = bounds.y
                    val itemX = eventX - workspaceX
                    val itemY = eventY - workspaceY
                    log.info("Accepting a " + event.toolType.getClass().getSimpleName())
                    addEventFrame(listenerController, event.toolType, itemX, itemY)
                case _ =>
                    log.error("??? can't accept drop of " + controller.getClass().getSimpleName())
            }
    }

    /** Add a frame to the workspace.
      *
      * @param controller
      * 		the controller for the frame
      * @param x
      * 		the x coordinate of the upper left corner of the frame
      * @param y
      * 		the y coordinate of the upper left corner of the frame
      */
    def addEventFrame(panelController: ListenerPanelController,
                      toolType: ToolType,
                      x: Int,
                      y: Int) = {
        val frameView = new WorkspaceFrame(toolType.getClass().getSimpleName());
        frameView.location = new Point(x, y)
        frameView.pack
        add(frameView)
        repaint
        // TODO I think this should be coming from the workspace controller
        val frameController = new WorkspaceFrameController(frameView, panelController)
        frameControllers += frameController
    }

    /** Compiles all active frames into their respective Glass code.<br/>
      * TODO9 Sean.. this doesn't make any sense. What would be the purpose of
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
