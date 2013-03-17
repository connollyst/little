package com.quane.little.ide

import com.quane.little.ide.language.FunctionPanel
import com.quane.little.ide.language.FunctionController
import com.quane.little.ide.language.statement.GetBoolStatementController
import com.quane.little.ide.language.statement.GetNumberStatementController
import com.quane.little.ide.language.statement.GetPointerStatementPanel
import com.quane.little.ide.language.statement.GetTextStatementController
import com.quane.little.ide.language.ListenerPanel
import com.quane.little.ide.language.ListenerController
import com.quane.little.ide.language.statement.SetBoolPointerStatementController
import com.quane.little.ide.language.statement.SetDirectionStatementPanel
import com.quane.little.ide.language.statement.SetDirectionStatementController
import com.quane.little.ide.language.statement.SetNumberPointerStatementController
import com.quane.little.ide.language.statement.SetPointerStatementPanel
import com.quane.little.ide.language.statement.SetSpeedStatementPanel
import com.quane.little.ide.language.statement.SetSpeedStatementController
import com.quane.little.ide.language.statement.SetTextPointerStatementController
import com.quane.little.ide.language.data.BoolExpressionPanel
import com.quane.little.ide.language.data.BoolExpressionController
import com.quane.little.ide.language.data.BoolFieldPanel
import com.quane.little.ide.language.data.BoolFieldController
import com.quane.little.ide.language.data.BoolController
import com.quane.little.ide.language.data.NumberExpressionPanel
import com.quane.little.ide.language.data.NumberExpressionController
import com.quane.little.ide.language.data.NumberExpressionController
import com.quane.little.ide.language.data.NumberFieldPanel
import com.quane.little.ide.language.data.NumberFieldController
import com.quane.little.ide.language.data.NumberFieldController
import com.quane.little.ide.language.data.NumberController
import com.quane.little.ide.language.data.NumberController
import com.quane.little.ide.language.data.TextExpressionPanel
import com.quane.little.ide.language.data.TextExpressionController
import com.quane.little.ide.language.data.TextFieldPanel
import com.quane.little.ide.language.data.TextFieldController
import com.quane.little.ide.language.data.TextController
import com.quane.little.ide.language.math.MathPanel
import com.quane.little.ide.language.math.MathController
import com.quane.little.ide.language.memory.PointerPanel
import com.quane.little.ide.language.memory.PointerController
import com.quane.little.language.data.Bool
import com.quane.little.language.data.Number
import com.quane.little.language.data.Text
import com.quane.little.language.event.GlassEvent

object PanelFactory {

    def getFunctionFrameFunction() = {
        () => new FunctionController(new FunctionPanel)
    }

    def getEventFrameFunction(event: GlassEvent) = {
        () => new ListenerController(event, new ListenerPanel)
    }

    //    def createPrintStatementPanel(): PrintStatementController = {
    //        val childController = createTextStatementPanel
    //        val childView = childController.view
    //        new PrintStatementController(
    //            new PrintStatementPanel(childView), childController
    //        )
    //    }

    def createMathPanel(): MathController = {
        new MathController(new MathPanel)
    }

    def createGetBoolStatementPanel(): GetBoolStatementController = {
        val pointerPanel = createBoolPointerPanel
        new GetBoolStatementController(
            new GetPointerStatementPanel(pointerPanel.view),
            pointerPanel
        )
    }

    def createGetNumberStatementPanel(): GetNumberStatementController = {
        val pointerPanel = createNumberPointerPanel
        new GetNumberStatementController(
            new GetPointerStatementPanel(pointerPanel.view),
            pointerPanel
        )
    }

    def createGetTextStatementPanel(): GetTextStatementController = {
        val pointerPanel = createTextPointerPanel
        new GetTextStatementController(
            new GetPointerStatementPanel(pointerPanel.view),
            pointerPanel
        )
    }

    def createSetBoolStatementPanel(): SetBoolPointerStatementController = {
        val pointerPanel = createBoolPointerPanel
        val valuePanel = new BoolExpressionController(new BoolExpressionPanel)
        new SetBoolPointerStatementController(
            new SetPointerStatementPanel(pointerPanel.view, valuePanel.view),
            pointerPanel,
            valuePanel
        )
    }

    def createSetNumberStatementPanel(): SetNumberPointerStatementController = {
        val pointerPanel = createNumberPointerPanel
        val valuePanel = new NumberExpressionController(new NumberExpressionPanel)
        new SetNumberPointerStatementController(
            new SetPointerStatementPanel(pointerPanel.view, valuePanel.view),
            pointerPanel,
            valuePanel
        )
    }

    def createSetTextStatementPanel(): SetTextPointerStatementController = {
        val pointerPanel = createTextPointerPanel
        val valuePanel = new TextExpressionController(new TextExpressionPanel)
        new SetTextPointerStatementController(
            new SetPointerStatementPanel(pointerPanel.view, valuePanel.view),
            pointerPanel,
            valuePanel
        )
    }

    def createSetSpeedStatementPanel(): SetSpeedStatementController = {
        val numberController = createNumberStatementPanel
        new SetSpeedStatementController(
            new SetSpeedStatementPanel(numberController.view),
            numberController
        )
    }

    def createSetDirectionStatementPanel(): SetDirectionStatementController = {
        val directionController = createNumberStatementPanel
        new SetDirectionStatementController(
            new SetDirectionStatementPanel(directionController.view),
            directionController
        )
    }

    def createBoolPointerPanel(): PointerController[Bool] = {
        new PointerController(new PointerPanel, classOf[Bool])
    }

    def createNumberPointerPanel(): PointerController[Number] = {
        new PointerController(new PointerPanel, classOf[Number])
    }

    def createTextPointerPanel(): PointerController[Text] = {
        new PointerController(new PointerPanel, classOf[Text])
    }

    def createNumberStatementPanel(): NumberExpressionController = {
        new NumberExpressionController(new NumberExpressionPanel)
    }

    def createBoolFieldPanel(): BoolFieldController = {
        new BoolFieldController(new BoolFieldPanel)
    }
    
    def createNumberFieldPanel(): NumberFieldController = {
        new NumberFieldController(new NumberFieldPanel)
    }
    
    def createTextFieldPanel(): TextFieldController = {
        new TextFieldController(new TextFieldPanel)
    }
}

sealed trait PanelType

case object FieldPanelType extends PanelType

case object ExpressionPanelType extends PanelType
