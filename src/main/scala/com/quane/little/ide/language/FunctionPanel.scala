package com.quane.little.ide.language

import scala.swing.GridPanel
import scala.swing.event.MouseEntered
import scala.swing.event.MouseExited
import org.eintr.loglady.Logging
import com.quane.little.ide.DragOutEvent
import com.quane.little.ide.DragOverEvent
import com.quane.little.ide.DropExpressionEvent
import com.quane.little.ide.IDE
import com.quane.little.ide.SetterToolType
import com.quane.little.ide.GetterToolType
import com.quane.little.ide.StepAddedEvent
import com.quane.little.ide.dnd.DragAndDropItem
import com.quane.little.ide.dnd.DragAndDropTarget
import com.quane.little.ide.swing.HighlightableComponent
import javax.swing.BorderFactory
import java.awt.Color
import scala.swing.BoxPanel
import scala.swing.Orientation

/** A function panel lets one build a little Function as a series of
  * Expressions.
  *
  * @author Sean Connolly
  */
class FunctionPanel
        extends BoxPanel(Orientation.Vertical)
        with ExpressionPanel
        with DragAndDropTarget
        with Logging {

    def accepts(item: DragAndDropItem): Boolean = {
        return item match {
            case SetterToolType => true
            case GetterToolType => true
            case _              => false
        }
    }

    def onDrop(event: DropExpressionEvent): Unit = {
        log.info("Accepting a " + event.toolType.getClass().getSimpleName())
        val controller = event.dropFunction()
        contents += controller.view
        publish(new StepAddedEvent(controller))
    }
}