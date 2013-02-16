package com.quane.glass.ide

import scala.collection.mutable.ListBuffer
import com.quane.glass.ide.language.WorkspaceFrameController
import com.quane.glass.ide.language.ListenerPanelController
import com.quane.glass.ide.language.WorkspaceFrame
import com.quane.glass.language.event.EventListener
import java.awt.Point
import scala.swing.Reactor
import org.eintr.loglady.Logging

class WorkspaceController(val view: WorkspacePanel)
        extends Reactor
        with Logging {

    val frameControllers = new ListBuffer[WorkspaceFrameController]()

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
                      y: Int) {
        val frameTitle = toolType.getClass().getSimpleName()
        val panelView = panelController.view
        val frameView = new WorkspaceFrame(frameTitle, panelView);
        val frameController = new WorkspaceFrameController(frameView, panelController)
        frameView.location = new Point(x, y)
        frameView.pack
        view.add(frameView)
        view.repaint
        frameControllers += frameController
    }

    /** Compiles all active frames into their respective Glass code.<br/>
      * TODO: Sean.. this doesn't make any sense. What would be the purpose of
      * compiling all _visible_ code?
      */
    def compileAll: List[EventListener] = {
        log.info("Compiling all workspace frames..")
        val eventListeners = new ListBuffer[EventListener]
        frameControllers.foreach(
            controller => {
                eventListeners += controller.compile
            }
        )
        eventListeners toList
    }

    listenTo(view)
    reactions += {
        case event: ListenerAddedEvent =>
            log.info("ListenerAddedEvent")
            addEventFrame(event.controller, event.toolType, event.x, event.y)
    }
}