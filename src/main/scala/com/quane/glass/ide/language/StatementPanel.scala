package com.quane.glass.ide.language

import scala.swing.BoxPanel
import scala.swing.Orientation
import scala.swing.Label
import scala.swing.TextField
import scala.swing.event.ValueChanged
import scala.swing.event.EditDone
import scala.swing.event.KeyTyped

trait StatementPanel
    extends GlassPanel

/** @author Sean Connolly
  */
class AssignmentStatementPanel(varName: String, varValue: String)
        extends BoxPanel(Orientation.Horizontal)
        with StatementPanel {

    /** Construct an assignment statement initialized with the given name. The
      * value field will be blank.
      */
    def this(variableName: String) {
        this(variableName, "")
    }

    /** Construct a blank assignment statement; both the name and value fields
      * will be blank.
      */
    def this() {
        this("")
    }

    private val nameField = new TextField(varName)
    private val valueField = new TextField(varValue)

    contents += new Label("Set")
    contents += nameField
    contents += new Label("to")
    contents += valueField

    def variableName: String = nameField.text

    def variableValue: String = valueField.text

}

/** @author Sean Connolly
  */
class SetSpeedStatementPanel
    extends LabeledStatementPanel("Speed");

/** @author Sean Connolly
  */
class SetDirectionStatementPanel
    extends LabeledStatementPanel("Direction");

/** @author Sean Connolly
  */
class PrintStatementPanel
    extends LabeledStatementPanel("Say");

abstract class LabeledStatementPanel(label: String)
        extends BoxPanel(Orientation.Horizontal)
        with StatementPanel {

    protected val field = new TextField

    contents += new Label(label + ":")
    contents += field

    def value: String = {
        field.text
    }

    listenTo(field)
    reactions += {
        case event: ValueChanged =>
        // TODO publish event to controller somehow
    }

}