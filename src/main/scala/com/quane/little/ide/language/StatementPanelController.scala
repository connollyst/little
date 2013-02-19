package com.quane.little.ide.language

import org.eintr.loglady.Logging
import com.quane.little.language.Expression
import com.quane.little.language.PrintStatement
import com.quane.little.language.Scope
import com.quane.little.language.SetDirectionStatement
import com.quane.little.language.SetSpeedStatement
import com.quane.little.language.SetterStatement
import com.quane.little.language.data.Direction
import com.quane.little.language.data.Number
import com.quane.little.language.data.Text
import com.quane.little.language.data.Value
import com.quane.little.language.memory.Pointer
import com.quane.little.game.entity.Mob

/** @author Sean Connolly
  */
abstract class StatementPanelController[E <: Expression[Any]](override val view: StatementPanel)
    extends ExpressionPanelController[E](view)

/** The controller for the {@link AssignmentStatementPanel} is  responsible for
  * creating a {@link AssignmentStatement} to represent that
  * represented by the panel.
  *
  * @author Sean Connolly
  */
class SetterStatementPanelController(override val view: SetterStatementPanel)
        extends StatementPanelController[SetterStatement[_ <: Value]](view)
        with Logging {

    override def validate {
        log.error("TODO: implement validate")
    }

    override def compile(scope: Scope): SetterStatement[_ <: Value] = {
        log.info("Compiling: Set " + view.variableName + "='" + view.variableValue + "'");
        val pointer = new Pointer(scope, view.variableName, classOf[Text])
        new SetterStatement(pointer, new Text(view.variableValue))
    }

}

class SetSpeedStatementPanelController(override val view: SetSpeedStatementPanel)
        extends StatementPanelController[SetSpeedStatement](view)
        with Logging {

    override def validate {
        log.error("TODO: implement validate")
    }

    override def compile(scope: Scope): SetSpeedStatement = {
        log.info("Compiling: Set " + Mob.VAR_SPEED + "='" + view.value + "'");
        new SetSpeedStatement(scope, new Number(view.value))
    }

}

class SetDirectionStatementPanelController(override val view: SetDirectionStatementPanel)
        extends StatementPanelController[SetDirectionStatement](view)
        with Logging {

    override def validate {
        log.error("TODO: implement validate")
    }

    override def compile(scope: Scope): SetDirectionStatement = {
        log.info("Compiling: Set " + Mob.VAR_DIRECTION + "='" + view.value + "'");
        new SetDirectionStatement(scope, new Direction(view.value))
    }

}

/** The controller for the {@link PrintStatementPanel} is responsible for
  * creating a {@link PrintStatement} to represent that
  * represented by the panel.
  *
  * @author Sean Connolly
  */
class PrintStatementPanelController(override val view: PrintStatementPanel)
        extends StatementPanelController[PrintStatement](view)
        with Logging {

    override def validate {
        // There's no way a print statement can be invalid, worst case scenario
        // is we are only printing an empty string
    }

    override def compile(scope: Scope): PrintStatement = {
        log.info("Compiling: Say '" + view.value + "'");
        new PrintStatement(view.value)
    }

}