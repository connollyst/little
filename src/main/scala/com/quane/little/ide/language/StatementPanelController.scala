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

class SetTextPointerExpressionStatementPanelController(
    override val view: SetStatementPanel,
    val pointerController: PointerPanelController[Text],
    override val valueController: TextExpressionPanelController)
        extends SetStatementPanelController[Text](view, valueController)
        with Logging {

    override def validate {
        log.error("TODO: implement validate")
    }

    override def compile(scope: Scope): SetStatement[Text] = {
        log.info("Compiling: Set TEXT = EXPRESSION[TEXT]");
        // new SetStatement(pointerController.compile(scope), valueController.compile(scope))
        null
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


/** The controller for the {@link PrintStatementPanel} is responsible for
  * creating a {@link PrintStatement} to represent that
  * represented by the panel.
  *
  * @author Sean Connolly
  */
//class PrintStatementPanelController(
//    override val view: PrintStatementPanel,
//    override val valueController: TextExpressionPanelController)
//        extends SetTextStatementPanelController(view, valueController) {
//
//    override def validate {
//        // There's no way a print statement can be invalid, worst case scenario
//        // is we are only printing an empty string
//    }
//
//    override def compile(scope: Scope): PrintStatement = {
//        //        log.info("Compiling: Say '" + view.value + "'");
//        new PrintStatement("todo")
//    }
//
//}