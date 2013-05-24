package com.quane.little.ide.layout.language

import com.quane.little.language.{Expression, Scope}
import com.quane.little.language.event.{LittleEvent, EventListener}
import org.eintr.loglady.Logging
import com.quane.little.ide.dnd.{DnDTarget, GetterToolType, SetterToolType, DragAndDropItem}
import javafx.scene.layout.VBox
import com.quane.little.ide.CustomControl

/** A listener panel is simply a specific type of {@link FunctionPane}.
  * That is, the UI for an event listener and a function behaves exactly the
  * same. The {@link ListenerController}, however, can {@code compile}
  * down to an {@link EventListener} by knowing what kind of {@link
  * LittleEvent} the {@link Function} is related to.
  *
  * @author Sean Connolly
  */
class ListenerPane(val event: LittleEvent)
  extends VBox
  with ExpressionPane[EventListener]
  with CustomControl
  with DnDTarget
  with Logging {

  // An event listener is just a function tied to an event, as such the event
  // listener pane is just a wrapper for the function pane with an event
  // attached.
  private val functionPane = new FunctionPane

  override def fxml = "ListenerPane.fxml"

  /** Can the getDragData be dropped here?
    *
    * @param item the drag and drop getDragData
    */
  override def accepts(item: DragAndDropItem): Boolean = {
    functionPane.accepts(item)
  }

  /** Handle a new [[com.quane.little.ide.layout.language.ExpressionPane]]
    * being dropped.
    *
    * @param pane the pane that was dropped
    */
  override def onDrop(pane: ExpressionPane[Expression[Any]]) {
    log.info("Accepting a " + pane)
    getChildren.add(pane)
  }

  override def compile(scope: Scope): EventListener = {
    log.info("Compiling: " + event.getClass.getSimpleName + " listener..")
    val function = functionPane.compile(scope)
    new EventListener(event, function)
  }

}
