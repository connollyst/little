package com.quane.little.ide.layout.language

import com.quane.little.language.{Expression, Scope, Function}
import org.eintr.loglady.Logging
import scala.collection.mutable.ListBuffer
import com.quane.little.ide.dnd.{DnDTarget, SetterToolType, DragAndDropItem}
import javafx.scene.layout.VBox
import com.quane.little.ide.CustomControl

/**
 *
 * @author Sean Connolly
 */
class FunctionPane
  extends VBox
  with ExpressionPane[Function]
  with CustomControl
  with DnDTarget
  with Logging {

  val steps = new ListBuffer[ExpressionPane[Expression[Any]]]()

  override def fxml = "FunctionPane.fxml"

  /** Can the item be dropped here?
    *
    * @param item the drag and drop item
    */
  override def accepts(item: DragAndDropItem): Boolean = {
    item match {
      case SetterToolType => true
      case _ => false
    }
  }

  /** Handle a new [[com.quane.little.ide.layout.language.ExpressionPane]]
    * being dropped.
    *
    * @param pane the pane that was dropped
    */
  override def onDrop(pane: ExpressionPane[Expression[Any]]) {
    log.info("Accepting a " + pane)
    steps += pane
    getChildren.add(pane)
  }

  override def compile(scope: Scope): Function = {
    log.info("Compiling: function..")
    val fun = new Function(scope) // TODO scope will always be null, yeah?
    for (step <- steps) {
      fun.addStep(step.compile(fun))
    }
    fun
  }
}
