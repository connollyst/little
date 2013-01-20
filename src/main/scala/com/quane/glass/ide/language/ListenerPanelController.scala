package com.quane.glass.ide.language

import org.eintr.loglady.Logging
import com.quane.glass.core.event.EventListener
import com.quane.glass.core.event.GlassEvent
import com.quane.glass.core.language.AssignmentStatement
import com.quane.glass.core.language.Expression
import com.quane.glass.core.language.Function
import com.quane.glass.core.language.Scope
import com.quane.glass.core.language.data.Text
import scala.swing.Reactor
import com.quane.glass.ide.StepAddedEvent

class ListenerPanelController(event: GlassEvent, view: ListenerPanel)
        extends ExpressionPanelController[EventListener](view)
        with Reactor
        with Logging {

    // An event listener is just a function tied to an event, as such the event
    // listener panel is just a wrapper for the function panel with an event
    // attached. When an event listener panel is created, in turn, we create a
    // function panel. 
    val functionController = new FunctionPanelController(new FunctionPanel)
    view.contents += functionController.view

    listenTo(view, functionController.view)
    reactions += {
        case event: StepAddedEvent =>
            log.info("StepAddedEvent")
//            addStep(event.controller)
        case _ =>
            log.error("whats this?")
    }

    /** {@inheritDoc}
      */
    override def validate: Unit = {
        // TODO
        log.error("TODO: implement ListenerPanelController.validate")
    }

    /** {@inheritDoc}
      */
    override def compile(scope: Scope): EventListener = {
        log.info("Compiling: " + event.getClass().getSimpleName() + " listener..")
        val fun = functionController.compile(scope)
        new EventListener(event, fun)
    }

}