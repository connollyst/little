package com.quane.little.ide.language.statement

import org.eintr.loglady.Logging

import com.quane.little.game.entity.Mob
import com.quane.little.ide.language.ExpressionPanelController
import com.quane.little.ide.language.data.BoolPanelController
import com.quane.little.ide.language.data.NumberExpressionPanelController
import com.quane.little.ide.language.data.NumberPanelController
import com.quane.little.ide.language.data.TextPanelController
import com.quane.little.ide.language.memory.PointerPanelController
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
abstract class SetStatementPanelController[V <: Value](
    override val view: SetStatementPanel,
    val valueController: ExpressionPanelController[Expression[V]])
        extends StatementPanelController[SetStatement[V]](view)

class SetPointerStatementPanelController[V <: Value](
    override val view: SetPointerStatementPanel,
    val pointerController: PointerPanelController[V],
    override val valueController: ExpressionPanelController[Expression[V]])
        extends SetStatementPanelController[V](view, valueController)
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

class SetBoolPointerStatementPanelController(
    override val view: SetPointerStatementPanel,
    override val pointerController: PointerPanelController[Bool],
    override val valueController: BoolPanelController)
        extends SetPointerStatementPanelController[Bool](view, pointerController, valueController)

class SetNumberPointerStatementPanelController(
    override val view: SetPointerStatementPanel,
    override val pointerController: PointerPanelController[Number],
    override val valueController: NumberPanelController)
        extends SetPointerStatementPanelController[Number](view, pointerController, valueController)

class SetTextPointerStatementPanelController(
    override val view: SetPointerStatementPanel,
    override val pointerController: PointerPanelController[Text],
    override val valueController: TextPanelController)
        extends SetPointerStatementPanelController[Text](view, pointerController, valueController)

class SetSpeedStatementPanelController(
    override val view: SetSpeedStatementPanel,
    override val valueController: NumberExpressionPanelController)
        extends SetStatementPanelController[Number](view, valueController)
        with Logging {

    override def validate {
        log.error("TODO: implement validate")
    }

    override def compile(scope: Scope): SetSpeedStatement = {
        log.info("Compiling: Set " + Mob.VAR_SPEED + " = EXPRESSION[NUMBER]");
        new SetSpeedStatement(scope, valueController.compile(scope))
    }
}

class SetDirectionStatementPanelController(
    override val view: SetDirectionStatementPanel,
    override val valueController: NumberExpressionPanelController)
        extends SetStatementPanelController[Number](view, valueController)
        with Logging {

    override def validate {
        log.error("TODO: implement validate")
    }

    override def compile(scope: Scope): SetDirectionStatement = {
        log.info("Compiling: Set " + Mob.VAR_DIRECTION + " = EXPRESSION[DIRECTION]");
        new SetDirectionStatement(scope, valueController.compile(scope))
    }
}
