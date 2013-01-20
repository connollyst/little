package com.quane.glass.ide.language

import org.eintr.loglady.Logging
import com.quane.glass.core.event.EventListener
import com.quane.glass.core.event.GlassEvent
import com.quane.glass.core.language.Function
import com.quane.glass.core.language.Expression

class WorkspaceFrameController(view: WorkspaceFrame, panelController: ListenerPanelController)
        extends Logging {

    def validate: Unit = {
        log.error("TODO: implement validate")
    }

    def compile: EventListener = {
        log.info("Compiling: WorkspaceFrameController..")
        panelController.compile(null);
    }

}