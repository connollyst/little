package com.quane.little.ide.language.math

import scala.swing.BoxPanel
import scala.swing.ComboBox
import scala.swing.Label
import scala.swing.Orientation
import com.quane.little.language.data.Number
import com.quane.little.ide.language.ExpressionPanel
import com.quane.little.ide.language.ExpressionPanelController
import javax.swing.BorderFactory
import com.quane.little.ide.language.data.NumberExpressionPanel
import com.quane.little.ide.language.data.NumberExpressionPanelController

class MathPanel
        extends BoxPanel(Orientation.Horizontal)
        with ExpressionPanel {

    val panelA = new NumberExpressionPanelController(new NumberExpressionPanel)
    val panelB = new NumberExpressionPanelController(new NumberExpressionPanel)

    contents += panelA.view
    contents += new ComboBox[String](List("Add", "Subtract"))
    contents += panelB.view
}