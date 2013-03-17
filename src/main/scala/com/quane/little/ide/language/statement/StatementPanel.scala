package com.quane.little.ide.language.statement

import scala.swing.BoxPanel
import scala.swing.Label
import scala.swing.Orientation
import scala.swing.event.ValueChanged
import com.quane.little.ide.language.data.NumberExpressionPanel
import com.quane.little.ide.language.memory.PointerPanel
import com.quane.little.ide.language.ExpressionPanel

abstract class StatementPanel
    extends BoxPanel(Orientation.Horizontal)
    with ExpressionPanel

//abstract class LabeledTextFieldStatementPanel(label: String)
//        extends SetStatementPanel(label) {
//
//    protected val field = new TextFieldPanel
//
//    contents += new Label(label + ":")
//    contents += field
//}

//abstract class LabeledNumberFieldStatementPanel(label: String)
//        extends BoxPanel(Orientation.Horizontal)
//        with StatementPanel
//        with Logging {
//
//    protected val field = new NumberFieldPanel
//
//    contents += new Label(label + ":")
//    contents += field
//}

//abstract class LabeledTextExpressionStatementPanel(
//    label: String,
//    override val valuePanel: TextExpressionPanel)
//        extends LabeledStatementPanel(label, valuePanel)

//class PrintStatementPanel(override val valuePanel: TextExpressionPanel)
//    extends LabeledTextExpressionStatementPanel("Say", valuePanel);

