package com.quane.little.ide.language

import org.eintr.loglady.Logging
import com.quane.little.language.PrintStatement
import com.quane.little.language.Scope
import com.quane.little.language.SetDirectionStatement
import com.quane.little.language.SetSpeedStatement
import com.quane.little.language.SetStatement
import com.quane.little.language.data.Number
import com.quane.little.language.data.Text
import com.quane.little.language.memory.Pointer
import com.quane.little.language.data.Value
import com.quane.little.language.Statement
import com.quane.little.language.Expression
import com.quane.little.game.entity.Mob
import com.quane.little.ide.language.memory.PointerPanelController
import com.quane.little.ide.language.data.NumberExpressionPanelController
import com.quane.little.ide.language.data.TextExpressionPanelController
import com.quane.little.ide.language.data.TextFieldPanelController
import com.quane.little.ide.language.data.TextPanelController

/** @author Sean Connolly
  */
abstract class StatementPanelController[S <: Statement[_]](override val view: StatementPanel)
    extends ExpressionPanelController[S](view)

/** The controller for the {@link AssignmentStatementPanel} is  responsible for
  * creating a {@link AssignmentStatement} to represent that
  * represented by the panel.
  *
  * @author Sean Connolly
  */
abstract class SetStatementPanelController[V <: Value](
    override val view: SetStatementPanel,
    val valueController: ExpressionPanelController[Expression[V]])
        extends StatementPanelController[SetStatement[V]](view)

class SetTextPointerStatementPanelController(
    override val view: SetStatementPanel,
    val pointerController: PointerPanelController[Text],
    override val valueController: TextPanelController)
        extends SetStatementPanelController[Text](view, valueController)
        with Logging {

    override def validate {
        log.error("TODO: implement validate")
    }

    override def compile(scope: Scope): SetStatement[Text] = {
        log.info("Compiling: Set TEXT = EXPRESSION[TEXT]");
        new SetStatement(pointerController.compile(scope), valueController.compile(scope))
    }

}

class SetNumberStatementPanelController(
    override val view: SetStatementPanel,
    override val valueController: NumberExpressionPanelController)
        extends SetStatementPanelController[Number](view, valueController)
        with Logging {

    override def validate {
        log.error("TODO: implement validate")
    }

    override def compile(scope: Scope): SetStatement[Number] = {
        log.info("Compiling: Set NUMBER = EXPRESSION[NUMBER]");
        // new SetStatement(pointerController.compile(scope), valueController.compile(scope))
        null
    }

}

class SetSpeedStatementPanelController(
    override val view: SetSpeedStatementPanel,
    override val valueController: NumberExpressionPanelController)
        extends SetNumberStatementPanelController(view, valueController) {

    override def compile(scope: Scope): SetSpeedStatement = {
        log.info("Compiling: Set " + Mob.VAR_SPEED + " = EXPRESSION[NUMBER]");
        new SetSpeedStatement(scope, valueController.compile(scope))
    }
}

class SetDirectionStatementPanelController(
    override val view: SetDirectionStatementPanel,
    override val valueController: NumberExpressionPanelController)
        extends SetNumberStatementPanelController(view, valueController) {

    override def compile(scope: Scope): SetDirectionStatement = {
        log.info("Compiling: Set " + Mob.VAR_DIRECTION + " = EXPRESSION[DIRECTION]");
        new SetDirectionStatement(scope, valueController.compile(scope))
    }
}
//}