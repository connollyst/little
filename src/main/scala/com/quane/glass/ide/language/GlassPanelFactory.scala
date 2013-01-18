package com.quane.glass.ide.language

import com.quane.glass.core.event.GlassEvent

object GlassPanelFactory {

    def getEventFrameFunction(event: GlassEvent) = {
        () => new ListenerPanelController(event, new ListenerPanel)
    }

    def createPrintStatementPanel(): PrintStatementPanelController = {
        new PrintStatementPanelController(new PrintStatementPanel)
    }

    def createAssignmentStatementPanel(): AssignmentStatementPanelController = {
        new AssignmentStatementPanelController(new AssignmentStatementPanel)
    }

    def createSetSpeedStatementPanel(): SetSpeedStatementPanelController = {
        new SetSpeedStatementPanelController(new SetSpeedStatementPanel)
    }

    def createSetDirectionStatementPanel(): SetDirectionStatementPanelController = {
        new SetDirectionStatementPanelController(new SetDirectionStatementPanel)
    }

}