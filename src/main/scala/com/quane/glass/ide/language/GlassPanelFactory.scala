package com.quane.glass.ide.language

import com.quane.glass.core.language.Expression

object GlassPanelFactory {

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