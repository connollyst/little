package com.quane.little.ide.language.statement

import org.eintr.loglady.Logging

import com.quane.little.game.entity.Mob
import com.quane.little.ide.language.ExpressionController
import com.quane.little.ide.language.data.BoolController
import com.quane.little.ide.language.data.NumberExpressionController
import com.quane.little.ide.language.data.NumberController
import com.quane.little.ide.language.data.TextController
import com.quane.little.ide.language.memory.PointerController
import com.quane.little.language.Expression
import com.quane.little.language.Scope
import com.quane.little.language.SetDirectionStatement
import com.quane.little.language.SetSpeedStatement
import com.quane.little.language.SetStatement
import com.quane.little.language.data.Bool
import com.quane.little.language.data.Number
import com.quane.little.language.data.Text
import com.quane.little.language.data.Value

/** The controller for the [[SetStatementPanel]] is responsible for creating a
  * [[SetStatement]] as represented by the view.
  *
  * @author Sean Connolly
  */
abstract class SetStatementController[V <: Value](
    override val view: SetStatementPanel,
    val valueController: ExpressionController[Expression[V]])
        extends StatementController[SetStatement[V]](view)

class SetPointerStatementController[V <: Value](
    override val view: SetPointerStatementPanel,
    val pointerController: PointerController[V],
    override val valueController: ExpressionController[Expression[V]])
        extends SetStatementController[V](view, valueController)
        with Logging {

    override def validate {
        // TODO check that the variable specified by the view is valid
        log.error("TODO: implement validate")
    }

    override def compile(scope: Scope): SetStatement[V] = {
        log.info("Compiling: Set " + view.variableName);
        new SetStatement(pointerController.compile(scope), valueController.compile(scope))
    }
}

class SetBoolPointerStatementController(
    override val view: SetPointerStatementPanel,
    override val pointerController: PointerController[Bool],
    override val valueController: BoolController)
        extends SetPointerStatementController[Bool](view, pointerController, valueController)

class SetNumberPointerStatementController(
    override val view: SetPointerStatementPanel,
    override val pointerController: PointerController[Number],
    override val valueController: NumberController)
        extends SetPointerStatementController[Number](view, pointerController, valueController)

class SetTextPointerStatementController(
    override val view: SetPointerStatementPanel,
    override val pointerController: PointerController[Text],
    override val valueController: TextController)
        extends SetPointerStatementController[Text](view, pointerController, valueController)

class SetSpeedStatementController(
    override val view: SetSpeedStatementPanel,
    override val valueController: NumberExpressionController)
        extends SetStatementController[Number](view, valueController)
        with Logging {

    override def validate {
        log.error("TODO: implement validate")
    }

    override def compile(scope: Scope): SetSpeedStatement = {
        log.info("Compiling: Set " + Mob.VAR_SPEED + " = EXPRESSION[NUMBER]");
        new SetSpeedStatement(scope, valueController.compile(scope))
    }
}

class SetDirectionStatementController(
    override val view: SetDirectionStatementPanel,
    override val valueController: NumberExpressionController)
        extends SetStatementController[Number](view, valueController)
        with Logging {

    override def validate {
        log.error("TODO: implement validate")
    }

    override def compile(scope: Scope): SetDirectionStatement = {
        log.info("Compiling: Set " + Mob.VAR_DIRECTION + " = EXPRESSION[DIRECTION]");
        new SetDirectionStatement(scope, valueController.compile(scope))
    }
}
