package com.quane.glass.ide.language

import org.eintr.loglady.Logging
import com.quane.glass.core.language.AssignmentStatement
import com.quane.glass.core.language.Expression
import com.quane.glass.core.language.PrintStatement
import com.quane.glass.core.language.SetSpeedStatement
import com.quane.glass.core.language.Scope
import com.quane.glass.core.language.data.Text
import com.quane.glass.core.Guy
import com.quane.glass.core.language.SetDirectionStatement

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
        log.error("TODO: implement AssignmentStatementPanelController.validate")
    }

    override def compile(scope: Scope): AssignmentStatement = {
        log.info("Compiling: Set " + view.variableName + "='" + view.variableValue + "'");
        new AssignmentStatement(scope, view.variableName, new Text(view.variableValue))
    }

}

class SetSpeedStatementPanelController(view: SetSpeedStatementPanel)
        extends StatementPanelController[SetSpeedStatement](view)
        with Logging {

    override def validate: Unit = {
        // TODO
        log.error("TODO: implement SetSpeedStatementPanelController.validate")
    }

    override def compile(scope: Scope): SetSpeedStatement = {
        log.info("Compiling: Set " + Guy.VAR_SPEED + "='" + view.value + "'");
        new SetSpeedStatement(scope, new Text(view.value))
    }

}

class SetDirectionStatementPanelController(view: SetDirectionStatementPanel)
        extends StatementPanelController[SetDirectionStatement](view)
        with Logging {

    override def validate: Unit = {
        // TODO
        log.error("TODO: implement SetSpeedStatementPanelController.validate")
    }

    override def compile(scope: Scope): SetDirectionStatement = {
        log.info("Compiling: Set " + Guy.VAR_DIRECTION + "='" + view.value + "'");
        new SetDirectionStatement(scope, new Text(view.value))
    }

}

/** The controller for the {@code PrintStatementPanel} is responsible for
  * creating a {@code PrintStatement} to represent that
  * represented by the panel.
  *
  * @author Sean Connolly
  */
class PrintStatementPanelController(view: PrintStatementPanel)
        extends StatementPanelController[PrintStatement](view)
        with Logging {

    override def validate: Unit = {
        // There's no way a print statement can be invalid, worst case scenario
        // is we are only printing an empty string
    }

    override def compile(scope: Scope): PrintStatement = {
        log.info("Compiling: Say '" + view.value + "'");
        new PrintStatement(view.value)
    }

}