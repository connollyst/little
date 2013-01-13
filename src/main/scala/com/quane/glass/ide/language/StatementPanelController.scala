package com.quane.glass.ide.language

import org.eintr.loglady.Logging

import com.quane.glass.core.language.AssignmentStatement
import com.quane.glass.core.language.Expression
import com.quane.glass.core.language.PrintStatement
import com.quane.glass.core.language.Scope
import com.quane.glass.core.language.data.Text

/** @author Sean Connolly
  */
abstract class StatementPanelController[E <: Expression[Any]](view: StatementPanel)
    extends GlassPanelController[E](view)

/** The controller for the {@code AssignmentStatementPanel} is  responsible for
  * creating a {@code AssignmentStatement} to represent that
  * represented by the panel.
  *
  * @author Sean Connolly
  */
class AssignmentStatementPanelController(view: AssignmentStatementPanel)
        extends StatementPanelController[AssignmentStatement](view)
        with Logging {

    override def validate: Unit = {
        // TODO
        log.error("TODO: implement AssignmentStatementPanel.validate")
    }

    override def compile(scope: Scope): AssignmentStatement = {
        println("Compiling: Set " + view.variableName + "='" + view.variableValue + "'");
        new AssignmentStatement(scope, view.variableName, new Text(view.variableValue))
    }

}

/** The controller for the {@code PrintStatementPanel} is responsible for
  * creating a {@code PrintStatement} to represent that
  * represented by the panel.
  *
  * @author Sean Connolly
  */
class PrintStatementPanelController(view: PrintStatementPanel)
        extends StatementPanelController[PrintStatement](view) {

    override def validate: Unit = {
        // There's no way a print statement can be invalid, worst case scenario
        // is we are only printing an empty string
    }

    override def compile(scope: Scope): PrintStatement = {
        println("Compiling: Say '" + view.printText + "'");
        new PrintStatement(view.printText)
    }

}