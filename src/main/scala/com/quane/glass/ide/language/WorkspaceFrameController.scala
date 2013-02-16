package com.quane.glass.ide.language

import org.eintr.loglady.Logging
import com.quane.glass.language.event.EventListener
import com.quane.glass.language.event.GlassEvent
import com.quane.glass.language.Function
import com.quane.glass.language.Expression

class WorkspaceFrameController(view: WorkspaceFrame, panelController: ListenerPanelController)
        extends Logging {

    def validate {
        log.error("TODO: implement validate")
    }

    def compile: EventListener = {
        log.info("Compiling: WorkspaceFrameController..")
        panelController.compile(null);
    }

}