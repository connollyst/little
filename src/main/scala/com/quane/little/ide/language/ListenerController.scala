package com.quane.little.ide.language

import org.eintr.loglady.Logging
import com.quane.little.language.event.EventListener
import com.quane.little.language.event.LittleEvent
import com.quane.little.language.Scope
import scala.swing.Reactor

class ListenerController(val event: LittleEvent, override val view: ListenerPanel)
        extends ExpressionController[EventListener](view)
        with Reactor
        with Logging {

    log.info("Creating a ListenerController for " + event.getClass().getSimpleName())

    // An event listener is just a function tied to an event, as such the event
    // listener panel is just a wrapper for the function panel with an event
    // attached. When an event listener panel is created, in turn, we create a
    // function panel. 
    val functionController = new FunctionController(view)

    /** {@inheritDoc}
      */
    override def validate {
        log.error("TODO: implement validate")
    }

    /** {@inheritDoc}
      */
    override def compile(scope: Scope): EventListener = {
        log.info("Compiling: " + event.getClass().getSimpleName() + " listener..")
        val function = functionController.compile(scope)
        new EventListener(event, function)
    }

}