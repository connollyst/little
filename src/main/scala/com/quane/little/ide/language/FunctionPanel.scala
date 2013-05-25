package com.quane.little.ide.language

import org.eintr.loglady.Logging
import com.quane.little.ide.DropExpressionEvent
import com.quane.little.ide.dnd.{SetterToolType, GetterToolType, DragAndDropItem, DragAndDropTarget}
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
      case _ => false
    }
  }

  def onDrop(event: DropExpressionEvent): Unit = {
    log.info("Accepting a " + event.toolType.getClass().getSimpleName())
    val controller = event.dropFunction()
    contents += controller.view
    // deleted publish
  }
}