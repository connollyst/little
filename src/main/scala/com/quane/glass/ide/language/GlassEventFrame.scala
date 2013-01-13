package com.quane.glass.ide.language

import java.awt.Dimension

import scala.collection.mutable.ListBuffer
import scala.swing.BoxPanel
import scala.swing.InternalFrame
import scala.swing.Orientation
import scala.swing.event.Event
import scala.swing.event.MouseEntered
import scala.swing.event.MouseExited

import javax.swing.BorderFactory

import com.google.common.eventbus.Subscribe
import com.quane.glass.core.event.GlassEvent
import com.quane.glass.core.language.Expression
import com.quane.glass.ide.DragDropEvent
import com.quane.glass.ide.DragOutProgramEvent
import com.quane.glass.ide.DragOverProgramEvent
import com.quane.glass.ide.GlassIDE
import com.quane.glass.ide.swing.HighlightableComponent

class GlassEventFrame(val event: GlassEvent)
        extends InternalFrame {

    title = event.toString
    visible = true
    resizable = true
    closable = true
    maximizable = true
    selected = true
    preferredSize = new Dimension(300, 150)

    val rootPanel = new ProgramRootPanel
    add(rootPanel)

    val steps = new ListBuffer[GlassPanelController[Expression[Any]]]()

    // Listen for the mouse entering and exiting the panel
    listenTo(mouse.moves)
    reactions += {
        case event: MouseEntered =>
            GlassIDE.eventBus.post(new ProgramEnteredEvent)
        case event: MouseExited =>
            GlassIDE.eventBus.post(new ProgramExitedEvent)
    }

    // Listen for drag & drop events on the event bus
    GlassIDE.eventBus.register(new ProgramDragAndDropEventListener(this))

    def highlight {
        rootPanel.highlight
    }

    def unhighlight {
        rootPanel.unhighlight
    }

    /**
      */
    def addStep(controller: GlassPanelController[Expression[Any]]) = {
        steps += controller
        rootPanel.contents += controller.view
    }

    /**
      */
    def stepPanels: List[GlassPanelController[Expression[Any]]] = {
        steps toList
    }

    /**
      */
    def compile: Unit = {
        println("Compiling event..")
        // TODO how does this work..?
        // when we go to run the code we need to give it a top level scope?
        steps.foreach(step => step.compile(null));
    }
}

class ProgramRootPanel
    extends BoxPanel(Orientation.Vertical)
    with HighlightableComponent

class ProgramEnteredEvent extends Event

class ProgramExitedEvent extends Event

class ProgramDragAndDropEventListener(myPanel: GlassEventFrame) {

    var overMe = false;

    @Subscribe
    def dragOverEvent(event: DragOverProgramEvent) {
        overMe = true;
        myPanel.highlight
    }
    @Subscribe
    def dragOutEvent(event: DragOutProgramEvent) {
        overMe = false
        myPanel.unhighlight
    }

    @Subscribe
    def dropEvent(event: DragDropEvent[GlassEventFrame]) {
        if (overMe) {
            // TODO compensate for offset in header
            val eventX = event.point.x
            val eventY = event.point.y
            val myX = myPanel.bounds.x
            val myY = myPanel.bounds.y
            val itemX = eventX - myX
            val itemY = eventY - myY
            println("Dropping tool: " + event.name)
            val controller = event.controllerFactoryFunction()
            myPanel.addStep(controller)
            myPanel.unhighlight
            myPanel.pack
        }
    }
}