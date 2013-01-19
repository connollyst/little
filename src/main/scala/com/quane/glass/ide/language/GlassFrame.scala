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
import com.quane.glass.ide.DragAndDropItem
import com.quane.glass.ide.DragAndDropTarget
import com.quane.glass.ide.DragOutEvent
import com.quane.glass.ide.DragOverEvent
import com.quane.glass.ide.DropExpressionEvent
import com.quane.glass.ide.IDE
import com.quane.glass.ide.swing.HighlightableComponent
import com.quane.glass.ide.SetterToolType

class GlassFrame(title: String)
        extends InternalFrame
        with DragAndDropTarget
        with Logging {

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
            log.info("GlassFrame.MouseEntered")
            IDE.eventBus.post(event)
        case event: MouseExited =>
            log.info("GlassFrame.MouseExited")
            IDE.eventBus.post(event)
        case event: DragOverEvent =>
            log.info("GlassFrame.DragOverEvent")
            highlight
        case event: DragOutEvent =>
            log.info("GlassFrame.DragOutEvent")
            unhighlight
        case event: DropExpressionEvent =>
            log.info("GlassFrame.DropExpressionEvent")
            unhighlight
            log.info("Dropping " + event.toolType)
            val controller = event.dropFunction()
    }

    // Listen for drag & drop events on the event bus
    IDE.eventBus.register(new ProgramDragAndDropEventListener(this))

    def highlight {
        rootPanel.highlight
    }

    def unhighlight {
        rootPanel.unhighlight
    }

    def accepts(item: DragAndDropItem): Boolean = {
        return item match {
            case SetterToolType => true
            case _              => false
        }
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
    def dragOverEvent(event: DragOverEvent) {
        // TODO check if I accept the dragged item
        overMe = true;
        frame.highlight
    }
    @Subscribe
    def dragOutEvent(event: DragOutEvent) {
        // TODO check if I accept the dragged item
        overMe = false
        frame.unhighlight
    }

    @Subscribe
    def dropEvent(event: DropExpressionEvent) {
        if (overMe) {
            log.info("Dropping " + event.toolType)
            val controller = event.dropFunction()
            //            frame.addStep(controller)
            frame.unhighlight
        }
    }
}