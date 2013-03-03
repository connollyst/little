package com.quane.little.ide.language

import org.eintr.loglady.Logging
import com.quane.little.language.PrintStatement
import com.quane.little.language.Scope
import com.quane.little.language.SetDirectionStatement
import com.quane.little.language.SetSpeedStatement
import com.quane.little.language.SetterStatement
import com.quane.little.language.data.Direction
import com.quane.little.language.data.Number
import com.quane.little.language.data.Text
import com.quane.little.language.memory.Pointer
import com.quane.little.language.data.Value
import com.quane.little.language.Statement
import com.quane.little.language.Expression

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
abstract class SetterStatementPanelController[V <: Value](
    override val view: SetterStatementPanel,
    val valueController: ExpressionPanelController[Expression[V]])
        extends StatementPanelController[SetterStatement[V]](view)

//abstract class SetAngleStatementPanelController(
//    override val view: SetterStatementPanel,
//    override val valueController: DirectionExpressionPanelController)
//        extends SetterStatementPanelController[Direction](view, valueController)

class SetNumberStatementPanelController(
    override val view: SetterStatementPanel,
    override val valueController: NumberExpressionPanelController)
        extends SetterStatementPanelController[Number](view, valueController)
        with Logging {

    override def validate {
        log.error("TODO: implement validate")
    }

    override def compile(scope: Scope): SetterStatement[Number] = {
        // log.info("Compiling: Set " + Mob.VAR_SPEED + "='" + view.value + "'");
        // new SetSpeedStatement(scope, valueController.compile(scope))
        null
    }

}

//abstract class SetTextStatementPanelController(
//    override val view: SetterStatementPanel,
//    override val valueController: TextExpressionPanelController)
//        extends SetterStatementPanelController[Text](view, valueController)

class SetSpeedStatementPanelController(
    override val view: SetSpeedStatementPanel,
    override val valueController: NumberExpressionPanelController)
        extends SetNumberStatementPanelController(view, valueController) {

    override def compile(scope: Scope): SetSpeedStatement = {
        // log.info("Compiling: Set " + Mob.VAR_SPEED + "='" + view.value + "'");
        new SetSpeedStatement(scope, valueController.compile(scope))
    }

}
//
//class SetDirectionStatementPanelController(
//    override val view: SetDirectionStatementPanel,
//    override val valueController: DirectionExpressionPanelController)
//        extends SetAngleStatementPanelController(view, valueController)
//        with Logging {
//
//    override def validate {
//        log.error("TODO: implement validate")
//    }
//
//    override def compile(scope: Scope): SetDirectionStatement = {
//        // log.info("Compiling: Set " + Mob.VAR_DIRECTION + "='" + view.value + "'");
//        //        new SetDirectionStatement(scope, valueController.compile(scope))
//        new SetDirectionStatement(scope, new Direction(10))
//    }
//
//}

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