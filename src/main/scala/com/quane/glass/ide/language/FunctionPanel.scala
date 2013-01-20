package com.quane.glass.ide.language

import java.awt.Dimension

import scala.swing.GridBagPanel
import scala.swing.GridBagPanel.Fill
import scala.swing.event.MouseEntered
import scala.swing.event.MouseExited

import org.eintr.loglady.Logging

import com.quane.glass.ide.DragAndDropItem
import com.quane.glass.ide.DragAndDropTarget
import com.quane.glass.ide.DragOutEvent
import com.quane.glass.ide.DragOverEvent
import com.quane.glass.ide.DropExpressionEvent
import com.quane.glass.ide.IDE
import com.quane.glass.ide.SetterToolType
import com.quane.glass.ide.StepAddedEvent
import com.quane.glass.ide.swing.HighlightableComponent

class FunctionPanel
        extends GridBagPanel
        with ExpressionPanel
        with DragAndDropTarget
        with HighlightableComponent
        with Logging {

    log.info("Creating a FunctionPanel")

    preferredSize = new Dimension(200, 200)

    listenTo(mouse.moves)
    reactions += {
        case event: MouseEntered =>
            log.info("MouseEntered")
            IDE.eventBus.post(event)
        case event: MouseExited =>
            log.info("MouseExited")
            IDE.eventBus.post(event)
        case event: DragOverEvent =>
            log.info("DragOverEvent")
            highlight
        case event: DragOutEvent =>
            log.info("DragOutEvent")
            unhighlight
        case event: DropExpressionEvent =>
            log.info("DropExpressionEvent")
            unhighlight
            log.info("Accepting a " + event.toolType.getClass().getSimpleName())
            val controller = event.dropFunction()
            val constraint = new Constraints
            constraint.fill = Fill.Horizontal
            add(controller.view, constraint)
            log.info("publishing a StepAddedEvent")
            publish(new StepAddedEvent(controller))
    }

    def accepts(item: DragAndDropItem): Boolean = {
        return item match {
            case SetterToolType => true
            case _              => false
        }
    }

}