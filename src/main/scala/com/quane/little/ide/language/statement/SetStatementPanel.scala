package com.quane.little.ide.language.statement

import com.quane.little.ide.language.memory.PointerPanel
import scala.swing.event.ValueChanged
import com.quane.little.ide.language.ExpressionPanel
import scala.swing.Label
import com.quane.little.ide.language.data.NumberExpressionPanel

/** A Swing panel to represent a [[SetStatementExpression]].
  *
  * @author Sean Connolly
  */
abstract class SetStatementPanel extends StatementPanel

/** A Swing panel to represent a [[SetStatementExpression]] which accepts an
  * [[ExpressionPanel]] as input.
  *
  * @author Sean Connolly
  */
class SetPointerStatementPanel(pointerPanel: PointerPanel, valuePanel: ExpressionPanel)
        extends SetStatementPanel {

    contents += pointerPanel
    contents += new Label("=")
    contents += valuePanel

    def variableName = pointerPanel.variableName
}

abstract class SetSpecialStatementPanel(label: String, valuePanel: ExpressionPanel)
        extends SetStatementPanel {

    contents += new Label(label + "=")
    contents += valuePanel

    listenTo(valuePanel, valuePanel.mouse.moves)
    reactions += {
        case event: ValueChanged =>
        // TODO publish event to controller somehow
    }
}
/** A labeled statement has the format: "Label: value"
  * Where the value is represented by an input box or an [[ExpressionPanel]]
  * of some sort.
  *
  * @author Sean Connolly
  */
abstract class LabeledStatementPanel(label: String, valuePanel: ExpressionPanel)
    extends SetSpecialStatementPanel(label, valuePanel)

abstract class LabeledNumberExpressionStatementPanel(label: String, valuePanel: NumberExpressionPanel)
    extends LabeledStatementPanel(label, valuePanel)

class SetSpeedStatementPanel(valuePanel: NumberExpressionPanel)
    extends LabeledNumberExpressionStatementPanel("Speed", valuePanel);

class SetDirectionStatementPanel(valuePanel: NumberExpressionPanel)
    extends LabeledNumberExpressionStatementPanel("Direction", valuePanel);
