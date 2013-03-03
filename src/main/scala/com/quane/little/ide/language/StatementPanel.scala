package com.quane.little.ide.language

import scala.swing.BoxPanel
import scala.swing.Label
import scala.swing.Orientation
import scala.swing.TextField
import scala.swing.event.ValueChanged
import java.awt.Dimension
import org.eintr.loglady.Logging
import com.quane.little.language.Expression

trait StatementPanel
    extends ExpressionPanel

/** @author Sean Connolly
  */
abstract class SetStatementPanel()
    extends BoxPanel(Orientation.Horizontal)
    with StatementPanel

class SetSpeedStatementPanel(override val valuePanel: NumberExpressionPanel)
    extends LabeledNumberExpressionStatementPanel("Speed", valuePanel);

class SetDirectionStatementPanel(override val valuePanel: DirectionExpressionPanel)
    extends LabeledDirectionExpressionStatementPanel("Direction", valuePanel);

//class PrintStatementPanel(override val valuePanel: TextExpressionPanel)
//    extends LabeledTextExpressionStatementPanel("Say", valuePanel);

//abstract class LabeledTextFieldStatementPanel(label: String)
//        extends SetStatementPanel(label) {
//
//    protected val field = new TextFieldPanel
//
//    contents += new Label(label + ":")
//    contents += field
//
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
//
//}

//abstract class LabeledTextExpressionStatementPanel(
//    label: String,
//    override val valuePanel: TextExpressionPanel)
//        extends LabeledStatementPanel(label, valuePanel)

abstract class LabeledNumberExpressionStatementPanel(
    label: String,
    override val valuePanel: NumberExpressionPanel)
        extends LabeledStatementPanel(label, valuePanel)

abstract class LabeledDirectionExpressionStatementPanel(
    label: String,
    override val valuePanel: DirectionExpressionPanel)
        extends LabeledStatementPanel(label, valuePanel)

/** A labeled statement has the format: "Label: [value]"
  * Where the value is represented by an input box or an
  * {@link ExpressionPanel} of some sort.
  *
  * @author Sean Connolly
  */
abstract class LabeledStatementPanel(label: String, val valuePanel: ExpressionPanel)
        extends SetStatementPanel
        with Logging {

    contents += new Label(label + ":")
    contents += valuePanel

    listenTo(valuePanel, valuePanel.mouse.moves)
    reactions += {
        case event: ValueChanged =>
            // TODO publish event to controller somehow
            log.info("Size: " + preferredSize)
    }

}
