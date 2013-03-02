package com.quane.little.ide.language

import java.awt.Dimension

import scala.swing.GridPanel
import scala.swing.event.MouseEntered
import scala.swing.event.MouseExited

import org.eintr.loglady.Logging

import com.quane.little.ide.DragAndDropItem
import com.quane.little.ide.DragAndDropTarget
import com.quane.little.ide.DragOutEvent
import com.quane.little.ide.DragOverEvent
import com.quane.little.ide.DropExpressionEvent
import com.quane.little.ide.IDE
import com.quane.little.ide.SetterToolType
import com.quane.little.ide.StepAddedEvent
import com.quane.little.ide.swing.HighlightableComponent

class FunctionPanel
        extends GridPanel(0, 1)
        with ExpressionPanel
        with DragAndDropTarget
        with HighlightableComponent
        with Logging {

    def accepts(item: DragAndDropItem): Boolean = {
        return item match {
            case SetterToolType => true
            case _              => false
        }
    }

    listenTo(mouse.moves)
    reactions += {
        case event: MouseEntered =>
            IDE.eventBus.post(event)
        case event: MouseExited =>
            IDE.eventBus.post(event)
        case event: DragOverEvent =>
            highlight
        case event: DragOutEvent =>
            unhighlight
        case event: DropExpressionEvent =>
            unhighlight
            log.info("Accepting a " + event.toolType.getClass().getSimpleName())
            val controller = event.dropFunction()
            contents += controller.view
            publish(new StepAddedEvent(controller))
    }

}