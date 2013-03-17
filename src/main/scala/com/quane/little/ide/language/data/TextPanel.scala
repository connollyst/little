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
import com.quane.little.ide.language.statement.GetStatementPanelController
import com.quane.little.ide.language.statement.GetStatementPanelController
import com.quane.little.ide.language.statement.GetTextStatementPanelController
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
        with HighlightableComponent
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
            publishDropEvent(event)
    }

    def publishDropEvent(event: DropExpressionEvent) = {
        log.info("Accepting a " + event.toolType.getClass().getSimpleName())
        event.toolType match {
            case GetTextToolType =>
                val controller = event.dropFunction()
                controller match {
                    case foo: GetTextStatementPanelController => {
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