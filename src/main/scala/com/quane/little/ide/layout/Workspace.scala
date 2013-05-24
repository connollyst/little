package com.quane.little.ide.layout

import javafx.scene.layout.FlowPane
import com.quane.little.ide.{DropExpressionEvent, CustomControl}
import com.quane.little.language.{Expression, Function}
import scala.collection.mutable.ListBuffer
import com.quane.little.language.event.EventListener
import org.eintr.loglady.Logging
import com.quane.little.ide.language._
import com.quane.little.ide.dnd.{EventToolType, DragAndDropItem, DnDTarget}
import com.quane.little.ide.layout.language.ExpressionPane

/**
 *
 * @author Sean Connolly
 */
class Workspace
  extends FlowPane
  with DnDTarget
  with CustomControl
  with Logging {

  def fxml: String = "Workspace.fxml"

  private val functionControllers = new
      ListBuffer[WorkspaceFunctionFrameController]()
  private val listenerControllers = new
      ListBuffer[WorkspaceListenerFrameController]()

  def accepts(item: DragAndDropItem): Boolean = {
    item match {
      case EventToolType => true
      case _ => false
    }
  }


  override def onDrop(pane: ExpressionPane[Expression[Any]]) {
    println("Dropped a " + pane)
    getChildren.add(pane)
  }

  /** Compiles all active frames into their respective little code.<br/>
    * TODO: Sean.. this doesn't make any sense. What would be the purpose of
    * compiling all _visible_ code?
    */
  def compileAllFunctions: List[Function] = {
    log.info("Compiling all functions..")
    val functions = new ListBuffer[Function]
    functionControllers.foreach(
      controller => {
        functions += controller.compile
      }
    )
    functions.toList
  }

  def compileAllListeners: List[EventListener] = {
    log.info("Compiling all event listeners..")
    val eventListeners = new ListBuffer[EventListener]
    listenerControllers.foreach(
      controller => {
        eventListeners += controller.compile
      }
    )
    eventListeners.toList
  }


}