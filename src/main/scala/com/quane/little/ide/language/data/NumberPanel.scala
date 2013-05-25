package com.quane.little.ide.language.data

import scala.swing.BoxPanel
import scala.swing.FlowPanel
import scala.swing.Label
import scala.swing.Orientation
import scala.swing.TextField

import org.eintr.loglady.Logging

import com.quane.little.ide.DropExpressionEvent
import com.quane.little.ide.dnd.{GetNumberToolType, DragAndDropItem, DragAndDropTarget}
import com.quane.little.ide.language.ExpressionPanel

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
        log.info("Accepting a " + event.toolType.getClass.getSimpleName)
        event.toolType match {
            case GetNumberToolType =>
                publishDropEvent(event)
            case _ =>
                log.warn("Cannot accept unrecognized tool type: " + event.toolType);
        }
    }

    private def publishDropEvent(event: DropExpressionEvent) = {
      // deleted publish
    }
}