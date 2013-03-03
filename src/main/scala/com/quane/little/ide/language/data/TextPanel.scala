package com.quane.little.ide.language.data

import com.quane.little.ide.language.ExpressionPanel
import scala.swing.TextField
import scala.swing.event.MouseEntered
import com.quane.little.ide.SetterToolType
import com.quane.little.ide.dnd.DragAndDropItem
import com.quane.little.ide.IDE
import scala.swing.event.MouseExited
import com.quane.little.ide.DragOverEvent
import com.quane.little.ide.swing.HighlightableComponent
import com.quane.little.ide.DragOutEvent
import com.quane.little.ide.DropExpressionEvent
import org.eintr.loglady.Logging
import com.quane.little.ide.NumberToolType
import scala.swing.FlowPanel
import com.quane.little.ide.TextToolType
import scala.swing.BoxPanel
import scala.swing.Orientation
import com.quane.little.ide.StepAddedEvent

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
        with Logging {

    def accepts(item: DragAndDropItem): Boolean = {
        return item match {
            case TextToolType => true
            case _            => false
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