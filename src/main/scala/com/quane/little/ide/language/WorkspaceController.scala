package com.quane.little.ide.language

import org.eintr.loglady.Logging
import com.quane.little.language.event.EventListener
import com.quane.little.language.event.LittleEvent
import com.quane.little.language.Function
import com.quane.little.language.Expression

sealed trait WorkspaceFrameController

class WorkspaceFunctionFrameController(view: WorkspaceFrame, panelController: FunctionController)
        extends WorkspaceFrameController
        with Logging {

    def validate {
        log.error("TODO: implement validate")
    }

    def compile: Function = {
        log.info("Compiling: WorkspaceFunctionFrameController..")
        panelController.compile(null);
    }

}

class WorkspaceListenerFrameController(view: WorkspaceFrame, panelController: ListenerController)
        extends WorkspaceFrameController
        with Logging {

    def validate {
        log.error("TODO: implement validate")
    }

    def compile: EventListener = {
        log.info("Compiling: WorkspaceListenerFrameController..")
        panelController.compile(null);
    }

}

