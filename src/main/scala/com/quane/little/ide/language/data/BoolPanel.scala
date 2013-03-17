package com.quane.little.ide.language.data

import scala.swing.BoxPanel
import scala.swing.FlowPanel
import scala.swing.Label
import scala.swing.Orientation
import scala.swing.TextField
import scala.swing.event.MouseEntered
import scala.swing.event.MouseExited

import org.eintr.loglady.Logging

import com.quane.little.ide.DragOutEvent
import com.quane.little.ide.DragOverEvent
import com.quane.little.ide.DropExpressionEvent
import com.quane.little.ide.GetBoolToolType
import com.quane.little.ide.GetterToolType
import com.quane.little.ide.IDE
import com.quane.little.ide.StepAddedEvent
import com.quane.little.ide.dnd.DragAndDropItem
import com.quane.little.ide.dnd.DragAndDropTarget
import com.quane.little.ide.language.ExpressionPanel
import com.quane.little.ide.swing.HighlightableComponent

import javax.swing.BorderFactory

trait BoolPanel
    extends ExpressionPanel

class BoolFieldPanel
        extends FlowPanel
        with BoolPanel {

    private val field = new TextField("", 16)

    contents += field

    // TODO flush this out
    def value: Boolean = field.text.equalsIgnoreCase("false")

}

class BoolExpressionPanel
        extends BoxPanel(Orientation.Vertical)
        with BoolPanel
        with DragAndDropTarget
        with Logging {

    val emptyLabel = new Label("Add")
    emptyLabel.border = BorderFactory.createEmptyBorder(20, 20, 20, 20);

    contents += emptyLabel

    def accepts(item: DragAndDropItem): Boolean = {
        return item match {
            case GetBoolToolType => true
            case GetterToolType  => true
            case _               => false
        }
    }

    def onDrop(event: DropExpressionEvent): Unit = {
        log.info("Accepting a " + event.toolType.getClass().getSimpleName())
        val controller = event.dropFunction()
        contents += controller.view
        publish(new StepAddedEvent(controller))
    }
}