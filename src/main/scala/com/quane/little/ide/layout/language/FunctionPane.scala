package com.quane.little.ide.layout.language

import com.quane.little.language.{Expression, Scope, Function}
import org.eintr.loglady.Logging
import scala.collection.mutable.ListBuffer
import com.quane.little.ide.language.ExpressionController
import com.quane.little.ide.dnd.{GetterToolType, SetterToolType, DragAndDropItem}
import com.quane.little.ide.StepAddedEvent

/**
 *
 * @author Sean Connolly
 */
class FunctionPane
  extends ExpressionPane[Function]
  with Logging {

  val steps = new ListBuffer[ExpressionController[Expression[Any]]]()

  def compile(scope: Scope): Function = {
    log.info("Compiling: function..")
    val fun = new Function(scope) // TODO scope will always be null, yeah?
    steps.foreach(
      step =>
        fun.addStep(step.compile(fun))
    );
    fun
  }

  /** Can the getDragData be dropped here?
    *
    * @param item the drag and drop getDragData
    */
  def accepts(item: DragAndDropItem): Boolean = {
    item match {
      case SetterToolType => true
      case GetterToolType => true
      case _ => false
    }
  }

  /** Handle a new [[com.quane.little.ide.layout.language.ExpressionPane]]
    * being dropped.
    *
    * @param pane the pane that was dropped
    */
  def onDrop(pane: ExpressionPane[Expression[Any]]) {
    log.info("Accepting a " + pane)
    getChildren.add(pane)
  }

}
