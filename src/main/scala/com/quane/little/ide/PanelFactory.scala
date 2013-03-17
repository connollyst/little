package com.quane.little.ide

import com.quane.little.ide.language.FunctionPanel
import com.quane.little.ide.language.FunctionPanelController
import com.quane.little.ide.language.GetBoolStatementPanelController
import com.quane.little.ide.language.GetNumberStatementPanelController
import com.quane.little.ide.language.GetPointerStatementPanel
import com.quane.little.ide.language.GetTextStatementPanelController
import com.quane.little.ide.language.ListenerPanel
import com.quane.little.ide.language.ListenerPanelController
import com.quane.little.ide.language.SetBoolPointerStatementPanelController
import com.quane.little.ide.language.SetDirectionStatementPanel
import com.quane.little.ide.language.SetDirectionStatementPanelController
import com.quane.little.ide.language.SetNumberPointerStatementPanelController
import com.quane.little.ide.language.SetPointerStatementPanel
import com.quane.little.ide.language.SetSpeedStatementPanel
import com.quane.little.ide.language.SetSpeedStatementPanelController
import com.quane.little.ide.language.SetTextPointerStatementPanelController
import com.quane.little.ide.language.data.BoolExpressionPanel
import com.quane.little.ide.language.data.BoolExpressionPanelController
import com.quane.little.ide.language.data.BoolFieldPanel
import com.quane.little.ide.language.data.BoolFieldPanelController
import com.quane.little.ide.language.data.BoolPanelController
import com.quane.little.ide.language.data.NumberExpressionPanel
import com.quane.little.ide.language.data.NumberExpressionPanelController
import com.quane.little.ide.language.data.NumberExpressionPanelController
import com.quane.little.ide.language.data.NumberFieldPanel
import com.quane.little.ide.language.data.NumberFieldPanelController
import com.quane.little.ide.language.data.NumberFieldPanelController
import com.quane.little.ide.language.data.NumberPanelController
import com.quane.little.ide.language.data.NumberPanelController
import com.quane.little.ide.language.data.TextExpressionPanel
import com.quane.little.ide.language.data.TextExpressionPanelController
import com.quane.little.ide.language.data.TextFieldPanel
import com.quane.little.ide.language.data.TextFieldPanelController
import com.quane.little.ide.language.data.TextPanelController
import com.quane.little.ide.language.math.MathPanel
import com.quane.little.ide.language.math.MathPanelController
import com.quane.little.ide.language.memory.PointerPanel
import com.quane.little.ide.language.memory.PointerPanelController
import com.quane.little.language.data.Bool
import com.quane.little.language.data.Number
import com.quane.little.language.data.Text
import com.quane.little.language.event.GlassEvent

object PanelFactory {

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

    def createMathPanel(): MathPanelController = {
        new MathPanelController(new MathPanel)
    }

    def createGetBoolStatementPanel(): GetBoolStatementPanelController = {
        val pointerPanel = createBoolPointerPanel
        new GetBoolStatementPanelController(
            new GetPointerStatementPanel(pointerPanel.view),
            pointerPanel
        )
    }

    def createGetNumberStatementPanel(): GetNumberStatementPanelController = {
        val pointerPanel = createNumberPointerPanel
        new GetNumberStatementPanelController(
            new GetPointerStatementPanel(pointerPanel.view),
            pointerPanel
        )
    }

    def createGetTextStatementPanel(): GetTextStatementPanelController = {
        val pointerPanel = createTextPointerPanel
        new GetTextStatementPanelController(
            new GetPointerStatementPanel(pointerPanel.view),
            pointerPanel
        )
    }

    def createSetBoolStatementPanel(): SetBoolPointerStatementPanelController = {
        val pointerPanel = createBoolPointerPanel
        val valuePanel = new BoolExpressionPanelController(new BoolExpressionPanel)
        new SetBoolPointerStatementPanelController(
            new SetPointerStatementPanel(pointerPanel.view, valuePanel.view),
            pointerPanel,
            valuePanel
        )
    }

    def createSetNumberStatementPanel(): SetNumberPointerStatementPanelController = {
        val pointerPanel = createNumberPointerPanel
        val valuePanel = new NumberExpressionPanelController(new NumberExpressionPanel)
        new SetNumberPointerStatementPanelController(
            new SetPointerStatementPanel(pointerPanel.view, valuePanel.view),
            pointerPanel,
            valuePanel
        )
    }

    def createSetTextStatementPanel(): SetTextPointerStatementPanelController = {
        val pointerPanel = createTextPointerPanel
        val valuePanel = new TextExpressionPanelController(new TextExpressionPanel)
        new SetTextPointerStatementPanelController(
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

    def createBoolPointerPanel(): PointerPanelController[Bool] = {
        new PointerPanelController(new PointerPanel, classOf[Bool])
    }

    def createNumberPointerPanel(): PointerPanelController[Number] = {
        new PointerPanelController(new PointerPanel, classOf[Number])
    }

    def createTextPointerPanel(): PointerPanelController[Text] = {
        new PointerPanelController(new PointerPanel, classOf[Text])
    }

    def createNumberStatementPanel(): NumberExpressionPanelController = {
        new NumberExpressionPanelController(new NumberExpressionPanel)
    }

    def createBoolFieldPanel(): BoolFieldPanelController = {
        new BoolFieldPanelController(new BoolFieldPanel)
    }
    
    def createNumberFieldPanel(): NumberFieldPanelController = {
        new NumberFieldPanelController(new NumberFieldPanel)
    }
    
    def createTextFieldPanel(): TextFieldPanelController = {
        new TextFieldPanelController(new TextFieldPanel)
    }
}

sealed trait PanelType

case object FieldPanelType extends PanelType

case object ExpressionPanelType extends PanelType
