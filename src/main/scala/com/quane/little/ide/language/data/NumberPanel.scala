package com.quane.little.ide.language.data

import scala.swing.BoxPanel
import scala.swing.FlowPanel
import scala.swing.Orientation
import scala.swing.TextField
import scala.swing.event.MouseEntered
import scala.swing.event.MouseExited
import org.eintr.loglady.Logging
import com.quane.little.ide.DragOutEvent
import com.quane.little.ide.DragOverEvent
import com.quane.little.ide.DropExpressionEvent
import com.quane.little.ide.GetNumberToolType
import com.quane.little.ide.IDE
import com.quane.little.ide.StepAddedEvent
import com.quane.little.ide.dnd.DragAndDropItem
import com.quane.little.ide.language.ExpressionPanel
import com.quane.little.ide.swing.HighlightableComponent
import com.quane.little.ide.GetterToolType

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
        with HighlightableComponent
        with Logging {

    def accepts(item: DragAndDropItem): Boolean = {
        return item match {
            case GetNumberToolType => true
            case GetterToolType    => true
            case _                 => false
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