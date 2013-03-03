package com.quane.little.ide

import com.quane.little.language.event.GlassEvent
import com.quane.little.language.data.Text
import com.quane.little.ide.language.memory.PointerPanel
import com.quane.little.ide.language.memory.PointerPanelController
import com.quane.little.ide.language.FunctionPanel
import com.quane.little.ide.language.FunctionPanelController
import com.quane.little.ide.language.ListenerPanel
import com.quane.little.ide.language.ListenerPanelController
import com.quane.little.ide.language.SetDirectionStatementPanel
import com.quane.little.ide.language.SetDirectionStatementPanelController
import com.quane.little.ide.language.SetPointerStatementPanel
import com.quane.little.ide.language.SetSpeedStatementPanel
import com.quane.little.ide.language.SetSpeedStatementPanelController
import com.quane.little.ide.language.SetTextPointerExpressionStatementPanelController
import com.quane.little.ide.language.data.NumberExpressionPanel
import com.quane.little.ide.language.data.NumberExpressionPanelController
import com.quane.little.ide.language.data.TextExpressionPanel
import com.quane.little.ide.language.data.TextExpressionPanelController

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

    def createAssignmentStatementPanel(): SetTextPointerExpressionStatementPanelController = {
        val pointerPanel = createTextPointerPanel
        val valuePanel = createTextStatementPanel
        new SetTextPointerExpressionStatementPanelController(
            new SetPointerStatementPanel(pointerPanel.view, valuePanel.view),
            pointerPanel,
            valuePanel
        )
    }

    def createSetSpeedStatementPanel(): SetSpeedStatementPanelController = {
        val numberPanelController = createNumberStatementPanel
        new SetSpeedStatementPanelController(
            new SetSpeedStatementPanel(numberPanelController.view),
            numberPanelController
        )
    }

    def createSetDirectionStatementPanel(): SetDirectionStatementPanelController = {
        val directionPanelController = createNumberStatementPanel
        new SetDirectionStatementPanelController(
            new SetDirectionStatementPanel(directionPanelController.view),
            directionPanelController
        )
    }

    def createTextPointerPanel(): PointerPanelController[Text] = {
        new PointerPanelController(new PointerPanel, classOf[Text])
    }

    def createTextStatementPanel(): TextExpressionPanelController = {
        new TextExpressionPanelController(new TextExpressionPanel)
    }

    def createNumberStatementPanel(): NumberExpressionPanelController = {
        new NumberExpressionPanelController(new NumberExpressionPanel)
    }

}