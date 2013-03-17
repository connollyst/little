package com.quane.little.ide.language.data

import scala.swing.BoxPanel
import scala.swing.FlowPanel
import scala.swing.Label
import scala.swing.Orientation
import scala.swing.TextField

import org.eintr.loglady.Logging

import com.quane.little.ide.DropExpressionEvent
import com.quane.little.ide.GetNumberStatementAddedEvent
import com.quane.little.ide.GetNumberToolType
import com.quane.little.ide.dnd.DragAndDropItem
import com.quane.little.ide.dnd.DragAndDropTarget
import com.quane.little.ide.language.ExpressionPanel
import com.quane.little.ide.language.statement.GetNumberStatementController

import javax.swing.BorderFactory

trait NumberPanel
    extends ExpressionPanel

class NumberFieldPanel
        extends FlowPanel
        with NumberPanel {

    private val field = new TextField("", 16)

    contents += field

    def value: Int = field.text.toInt

}

class NumberExpressionPanel
        extends BoxPanel(Orientation.Vertical)
        with NumberPanel
        with DragAndDropTarget
        with Logging {

    val emptyLabel = new Label("Add")
    emptyLabel.border = BorderFactory.createEmptyBorder(20, 20, 20, 20);

    contents += emptyLabel

    def accepts(item: DragAndDropItem): Boolean = {
        return item match {
            case GetNumberToolType => true
            case _                 => false
        }
    }

    def onDrop(event: DropExpressionEvent): Unit = {
        log.info("Accepting a " + event.toolType.getClass().getSimpleName())
        event.toolType match {
            case GetNumberToolType =>
                publishDropEvent(event)
            case _ =>
                log.warn("Cannot accept unrecognized tool type: " + event.toolType);
        }
    }

    private def publishDropEvent(event: DropExpressionEvent) = {
        val controller = event.dropFunction()
        controller match {
            case foo: GetNumberStatementController => {
                contents.clear
                contents += controller.view;
                val x = event.point.x;
                val y = event.point.y;
                publish(
                    new GetNumberStatementAddedEvent(foo, event.toolType, x, y)
                )
                log.info("published GetNumberStatementAddedEvent")
            }
            case _ =>
                log.error("Cannot accept unrecognized controller: " + controller.getClass)
        }
    }
}