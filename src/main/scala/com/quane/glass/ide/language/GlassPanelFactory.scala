package com.quane.glass.ide.language

import com.quane.glass.core.language.Expression

object GlassPanelFactory {

    def createAssignmentStatementPanel(): AssignmentStatementPanelController = {
        new AssignmentStatementPanelController(new AssignmentStatementPanel);
    }

    def createPrintStatementPanel(): PrintStatementPanelController = {
        new PrintStatementPanelController(new PrintStatementPanel)
    }
    
}