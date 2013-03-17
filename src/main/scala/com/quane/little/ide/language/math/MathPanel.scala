package com.quane.little.ide.language.math

import scala.swing.BoxPanel
import scala.swing.ComboBox
import scala.swing.Label
import scala.swing.Orientation
import com.quane.little.language.data.Number
import com.quane.little.ide.language.ExpressionPanel
import com.quane.little.ide.language.ExpressionController
import javax.swing.BorderFactory
import com.quane.little.ide.language.data.NumberExpressionPanel
import com.quane.little.ide.language.data.NumberExpressionController

class MathPanel
        extends BoxPanel(Orientation.Horizontal)
        with ExpressionPanel {

    val panelA = new NumberExpressionController(new NumberExpressionPanel)
    val panelB = new NumberExpressionController(new NumberExpressionPanel)

    contents += panelA.view
    contents += new ComboBox[String](List("Add", "Subtract"))
    contents += panelB.view
}