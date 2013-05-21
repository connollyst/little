package com.quane.little.ide.layout.language

import com.quane.little.language.Scope
import com.quane.little.language.event.{LittleEvent, EventListener}
import org.eintr.loglady.Logging

/** A listener panel is simply a specific type of {@link FunctionPane}.
  * That is, the UI for an event listener and a function behaves exactly the
  * same. The {@link ListenerController}, however, can {@code compile}
  * down to an {@link EventListener} by knowing what kind of {@link
  * LittleEvent} the {@link Function} is related to.
  *
  * @author Sean Connolly
  */
class ListenerPane(val event: LittleEvent)
  extends ExpressionPane[EventListener]
  with Logging {

  // An event listener is just a function tied to an event, as such the event
  // listener pane is just a wrapper for the function pane with an event
  // attached.
  private val functionPane = new FunctionPane

  /** {@inheritDoc}
    */
  override def compile(scope: Scope): EventListener = {
    log.info("Compiling: " + event.getClass.getSimpleName + " listener..")
    val function = functionPane.compile(scope)
    new EventListener(event, function)
  }

}
