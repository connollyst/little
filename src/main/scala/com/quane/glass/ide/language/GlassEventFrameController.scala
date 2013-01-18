package com.quane.glass.ide.language

import org.eintr.loglady.Logging
import com.quane.glass.core.language.Function
import com.quane.glass.core.event.EventListener
import com.quane.glass.core.event.GlassEvent

class GlassFrameController(event: GlassEvent, val view: GlassFrame)
        extends Logging {

    def validate: Unit = {
        // TODO
        log.error("TODO: implement EventFrameController.validate")
    }

    def compile: EventListener = {
        log.info("Compiling: glass frame..")
        null // TODO
    }

}