package com.quane.little.ide.language

import com.quane.little.language.event.GlassEvent

object GlassPanelFactory {

    def getFunctionFrameFunction() = {
        () => new FunctionPanelController(new FunctionPanel)
    }

    def getEventFrameFunction(event: GlassEvent) = {
        () => new ListenerPanelController(event, new ListenerPanel)
    }

//    def createPrintStatementPanel(): PrintStatementPanelController = {
//        val childController = createTextStatementPanel
//        val childView = childController.view
//        new PrintStatementPanelController(
//            new PrintStatementPanel(childView), childController
//        )
//    }
//
//    def createAssignmentStatementPanel(): SetterStatementPanelController = {
//        new SetterStatementPanelController(
//            new SetterStatementPanel
//        )
//    }

    def createSetSpeedStatementPanel(): SetSpeedStatementPanelController = {
        val numberPanelController = createNumberStatementPanel
        new SetSpeedStatementPanelController(
            new SetSpeedStatementPanel(numberPanelController.view),
            numberPanelController
        )
    }

//    def createSetDirectionStatementPanel(): SetDirectionStatementPanelController = {
//        val directionPanelController = createDirectionStatementPanel
//        new SetDirectionStatementPanelController(
//            new SetDirectionStatementPanel(directionPanelController.view),
//            directionPanelController
//        )
//    }
//
//    def createTextStatementPanel(): TextExpressionPanelController = {
//        new TextExpressionPanelController(new TextExpressionPanel)
//    }

    def createNumberStatementPanel(): NumberExpressionPanelController = {
        new NumberExpressionPanelController(new NumberExpressionPanel)
    }

//    def createDirectionStatementPanel(): DirectionExpressionPanelController = {
//        new DirectionExpressionPanelController(new DirectionExpressionPanel)
//    }

}