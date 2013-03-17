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
import com.quane.little.ide.GetTextToolType
import com.quane.little.ide.IDE
import com.quane.little.ide.StepAddedEvent
import com.quane.little.ide.dnd.DragAndDropItem
import com.quane.little.ide.dnd.DragAndDropTarget
import com.quane.little.ide.language.ExpressionPanel
import com.quane.little.ide.swing.HighlightableComponent
import com.quane.little.ide.GetterToolType
import com.quane.little.ide.GetTextStatementAddedEvent
import com.quane.little.ide.language.statement.GetStatementController
import com.quane.little.ide.language.statement.GetStatementController
import com.quane.little.ide.language.statement.GetTextStatementController
import javax.swing.BorderFactory
import java.awt.Color
import java.awt.Dimension

trait TextPanel
    extends ExpressionPanel

class TextFieldPanel
        extends FlowPanel
        with TextPanel {

    private val field = new TextField("", 16)

    contents += field

    def value: String = field.text

}

class TextExpressionPanel
        extends BoxPanel(Orientation.Vertical)
        with TextPanel
        with DragAndDropTarget
        with Logging {

    val emptyLabel = new Label("Add")
    emptyLabel.border = BorderFactory.createEmptyBorder(20, 20, 20, 20);

    contents += emptyLabel

    def accepts(item: DragAndDropItem): Boolean = {
        return item match {
            case GetTextToolType => true
            case _               => false
        }
    }

    def onDrop(event: DropExpressionEvent): Unit = {
        log.info("Accepting a " + event.toolType.getClass().getSimpleName())
        event.toolType match {
            case GetTextToolType =>
                val controller = event.dropFunction()
                controller match {
                    case foo: GetTextStatementController => {
                        contents.clear
                        contents += controller.view;
                        val x = event.point.x;
                        val y = event.point.y;
                        publish(
                            new GetTextStatementAddedEvent(foo, event.toolType, x, y)
                        )
                        log.info("published GetTextStatementAddedEvent")
                    }
                    case _ =>
                        log.error("Cannot accept unrecognized controller: " + controller.getClass)
                }
            case _ =>
                log.warn("Cannot accept unrecognized tool type: " + event.toolType);
        }

    }

}