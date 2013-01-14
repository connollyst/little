package com.quane.glass.ide.language

import org.eintr.loglady.Logging
import com.quane.glass.core.language.Function
import com.quane.glass.core.event.EventListener

class GlassEventFrameController(view: GlassEventFrame)
        extends Logging {

    def validate: Unit = {
        // TODO
        log.error("TODO: implement EventFrameController.validate")
    }

    def compile: EventListener = {
        // TODO move this to the controller
        log.info("Compiling: " + view.event.getClass().getSimpleName() + " listener..")
        val fun = new Function
        view.stepPanels.foreach(
            step =>
                fun.addStep(step.compile(fun))
        );
        new EventListener(view.event, fun)
    }

}