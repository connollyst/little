package com.quane.glass.ide.language

import java.awt.Dimension

import scala.collection.mutable.ListBuffer
import scala.swing.BoxPanel
import scala.swing.InternalFrame
import scala.swing.Orientation
import scala.swing.event.Event
import scala.swing.event.MouseEntered
import scala.swing.event.MouseExited

import org.eintr.loglady.Logging

import com.google.common.eventbus.Subscribe
import com.quane.glass.core.language.Expression
import com.quane.glass.ide.DragOutEvent
import com.quane.glass.ide.DragOverEvent
import com.quane.glass.ide.DropExpressionEvent
import com.quane.glass.ide.GlassIDE
import com.quane.glass.ide.swing.HighlightableComponent

class GlassFrame(title: String)
        extends InternalFrame {

    super.title = title
    visible = true
    resizable = true
    closable = true
    maximizable = true
    selected = true
    preferredSize = new Dimension(300, 150)

    val rootPanel = new ProgramRootPanel
    add(rootPanel)

    val steps = new ListBuffer[ExpressionPanelController[Expression[Any]]]()

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

}

class ProgramRootPanel
    extends BoxPanel(Orientation.Vertical)
    with HighlightableComponent

class ProgramEnteredEvent extends Event

class ProgramExitedEvent extends Event

class ProgramDragAndDropEventListener(frame: GlassFrame)
        extends Logging {

    var overMe = false;

    @Subscribe
    def dragOverEvent(event: DragOverEvent[Any]) {
        // TODO check if I accept the dragged item
        overMe = true;
        frame.highlight
    }
    @Subscribe
    def dragOutEvent(event: DragOutEvent[Any]) {
        // TODO check if I accept the dragged item
        overMe = false
        frame.unhighlight
    }

    @Subscribe
    def dropEvent(event: DropExpressionEvent[GlassFrame]) {
        if (overMe) {
            log.info("Dropping tool: " + event.name)
            val controller = event.dropFunction()
            //            frame.addStep(controller)
            frame.unhighlight
        }
    }
}