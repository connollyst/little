package com.quane.glass.ide.language

import org.eintr.loglady.Logging
import com.quane.glass.core.event.EventListener
import com.quane.glass.core.event.GlassEvent
import com.quane.glass.core.language.SetterStatement
import com.quane.glass.core.language.Expression
import com.quane.glass.core.language.Function
import com.quane.glass.core.language.Scope
import com.quane.glass.core.language.data.Text
import scala.swing.Reactor
import com.quane.glass.ide.StepAddedEvent

class ListenerPanelController(val event: GlassEvent, override val view: ListenerPanel)
        extends ExpressionPanelController[EventListener](view)
        with Reactor
        with Logging {

    log.info("Creating a ListenerPanelController for " + event.getClass().getSimpleName())

    // An event listener is just a function tied to an event, as such the event
    // listener panel is just a wrapper for the function panel with an event
    // attached. When an event listener panel is created, in turn, we create a
    // function panel. 
    val functionController = new FunctionPanelController(view)

    /** {@inheritDoc}
      */
    override def validate: Unit = {
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