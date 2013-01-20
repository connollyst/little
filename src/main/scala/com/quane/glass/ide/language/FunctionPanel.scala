package com.quane.glass.ide.language

import scala.collection.mutable.ListBuffer
import scala.swing.BoxPanel
import scala.swing.Orientation
import scala.swing.event.MouseEntered
import scala.swing.event.MouseExited
import org.eintr.loglady.Logging
import com.quane.glass.core.language.Expression
import com.quane.glass.ide.SetterToolType
import com.quane.glass.ide.DragAndDropItem
import com.quane.glass.ide.IDE
import com.quane.glass.ide.DragAndDropTarget
import com.quane.glass.ide.DragOverEvent
import com.quane.glass.ide.DragOutEvent
import com.quane.glass.ide.DropExpressionEvent
import com.quane.glass.ide.swing.HighlightableComponent
import com.quane.glass.ide.StepAddedEvent

class FunctionPanel
        extends BoxPanel(Orientation.Vertical)
        with ExpressionPanel
        with DragAndDropTarget
        with HighlightableComponent
        with Logging {

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
            log.info("Accepting " + event.toolType)
            val controller = event.dropFunction()
            contents += controller.view
            publish(new StepAddedEvent(controller))
    }

    def accepts(item: DragAndDropItem): Boolean = {
        return item match {
            case SetterToolType => true
            case _              => false
        }
    }

}