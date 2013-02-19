package com.quane.little.ide.language

import org.eintr.loglady.Logging
import com.quane.little.language.event.EventListener
import com.quane.little.language.event.GlassEvent
import com.quane.little.language.Function
import com.quane.little.language.Expression

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