package com.quane.glass.ide.language

import org.eintr.loglady.Logging

import com.quane.glass.core.event.EventListener
import com.quane.glass.core.event.GlassEvent
import com.quane.glass.core.language.AssignmentStatement
import com.quane.glass.core.language.Expression
import com.quane.glass.core.language.Function
import com.quane.glass.core.language.Scope
import com.quane.glass.core.language.data.Text

class ListenerPanelController(event: GlassEvent, view: ListenerPanel)
        extends ExpressionPanelController[EventListener](view)
        with Logging {

    override def validate: Unit = {
        // TODO
        log.error("TODO: implement ListenerPanelController.validate")
    }

    override def compile(scope: Scope): EventListener = {
        log.info("Compiling: " + event.getClass().getSimpleName() + " listener..")
        val fun = new Function
        view.stepPanels.foreach(
            step =>
                fun.addStep(step.compile(fun))
        );
        new EventListener(event, fun)
    }
}