package com.quane.little.ide

import java.awt.Point

import scala.collection.mutable.ListBuffer
import scala.swing.Reactor

import org.eintr.loglady.Logging

import com.quane.little.ide.language.FunctionController
import com.quane.little.ide.language.ListenerController
import com.quane.little.ide.language.WorkspaceFrame
import com.quane.little.ide.language.WorkspaceFrameController
import com.quane.little.ide.language.WorkspaceFunctionFrameController
import com.quane.little.ide.language.WorkspaceListenerFrameController
import com.quane.little.language.event.EventListener
import com.quane.little.language.Function
import com.quane.little.ide.dnd.ToolType

class WorkspaceController(val view: WorkspacePanel)
        extends Reactor
        with Logging {

    val functionControllers = new ListBuffer[WorkspaceFunctionFrameController]()
    val listenerControllers = new ListBuffer[WorkspaceListenerFrameController]()

    /** Add a function frame to the workspace.
      *
      * @param panelController
      * 		the controller for the frame
      * @param x
      * 		the x coordinate of the upper left corner of the frame
      * @param y
      * 		the y coordinate of the upper left corner of the frame
      */
    def addFunctionFrame(panelController: FunctionController,
                         toolType: ToolType,
                         x: Int,
                         y: Int) {
        val frameTitle = toolType.getClass().getSimpleName()
        val panelView = panelController.view
        val frameView = new WorkspaceFrame(frameTitle, panelView);
        val frameController = new WorkspaceFunctionFrameController(frameView, panelController)
        frameView.location = new Point(x, y)
        frameView.pack
        view.add(frameView)
        view.repaint
        functionControllers += frameController
    }

    /** Add a listener frame to the workspace.
      *
      * @param panelController
      * 		the controller for the frame
      * @param x
      * 		the x coordinate of the upper left corner of the frame
      * @param y
      * 		the y coordinate of the upper left corner of the frame
      */
    def addEventFrame(panelController: ListenerController,
                      toolType: ToolType,
                      x: Int,
                      y: Int) {
        val frameTitle = toolType.getClass().getSimpleName()
        val panelView = panelController.view
        val frameView = new WorkspaceFrame(frameTitle, panelView);
        val frameController = new WorkspaceListenerFrameController(frameView, panelController)
        frameView.location = new Point(x, y)
        frameView.pack
        view.add(frameView)
        view.repaint
        listenerControllers += frameController
    }

    /** Compiles all active frames into their respective little code.<br/>
      * TODO: Sean.. this doesn't make any sense. What would be the purpose of
      * compiling all _visible_ code?
      */
    def compileAllFunctions: List[Function] = {
        log.info("Compiling all functions..")
        val functions = new ListBuffer[Function]
        functionControllers.foreach(
            controller => {
                functions += controller.compile
            }
        )
        functions toList
    }

    def compileAllListeners: List[EventListener] = {
        log.info("Compiling all event listeners..")
        val eventListeners = new ListBuffer[EventListener]
        listenerControllers.foreach(
            controller => {
                eventListeners += controller.compile
            }
        )
        eventListeners toList
    }

    listenTo(view)
    reactions += {
        case event: FunctionAddedEvent =>
            log.info("FunctionAddedEvent")
            addFunctionFrame(event.controller, event.toolType, event.x, event.y)
        case event: ListenerAddedEvent =>
            log.info("ListenerAddedEvent")
            addEventFrame(event.controller, event.toolType, event.x, event.y)
    }

}