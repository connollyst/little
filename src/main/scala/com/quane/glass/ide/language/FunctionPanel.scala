package com.quane.glass.ide.language

import scala.swing.BoxPanel
import scala.swing.Orientation
import scala.collection.mutable.ListBuffer
import com.quane.glass.core.language.Expression

abstract class FunctionPanel
        extends BoxPanel(Orientation.Horizontal)
        with ExpressionPanel {

    val steps = new ListBuffer[ExpressionPanelController[Expression[Any]]]()

    def stepPanels: List[ExpressionPanelController[Expression[Any]]] = steps toList

    def addStep(controller: ExpressionPanelController[Expression[Any]]) = {
        steps += controller
        contents += controller.view
    }
    
}