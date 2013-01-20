package com.quane.glass.ide.language

import org.eintr.loglady.Logging
import com.quane.glass.core.event.EventListener
import com.quane.glass.core.event.GlassEvent
import com.quane.glass.core.language.Function
import com.quane.glass.core.language.Expression

class WorkspaceFrameController(
    view: WorkspaceFrame,
    panelController: ExpressionPanelController[Expression[Any]])
        extends Logging {

    def validate: Unit = {
        // TODO
        log.error("TODO: implement EventFrameController.validate")
    }

    def compile: EventListener = {
        log.info("Compiling: glass frame..")
        //        val function = view.
        //        val listener = new EventListener(event, function: Function)
        null // TODO
    }

}