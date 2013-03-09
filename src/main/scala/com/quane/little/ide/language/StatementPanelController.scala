package com.quane.little.ide.language

import org.eintr.loglady.Logging
import com.quane.little.game.entity.Mob
import com.quane.little.ide.language.data.NumberExpressionPanelController
import com.quane.little.ide.language.data.TextPanelController
import com.quane.little.ide.language.memory.PointerPanelController
import com.quane.little.language.Expression
import com.quane.little.language.GetStatement
import com.quane.little.language.Scope
import com.quane.little.language.SetDirectionStatement
import com.quane.little.language.SetSpeedStatement
import com.quane.little.language.SetStatement
import com.quane.little.language.Statement
import com.quane.little.language.data.Number
import com.quane.little.language.data.Text
import com.quane.little.language.data.Value
import com.quane.little.language.memory.Pointer
import com.quane.little.ide.language.data.NumberPanelController

/** @author Sean Connolly
  */
abstract class StatementPanelController[S <: Statement[_]](override val view: StatementPanel)
    extends ExpressionPanelController[S](view)

/** The controller for the [[GetStatementPanel]] is responsible for creating a
  * [[GetStatement]] as represented by the view.
  *
  * @author Sean Connolly
  */
abstract class GetStatementPanelController[V <: Value](
    override val view: GetStatementPanel)
        extends StatementPanelController[GetStatement[V]](view)

/** The controller for the [[SetStatementPanel]] is responsible for creating a
  * [[SetStatement]] as represented by the view.
  *
  * @author Sean Connolly
  */
abstract class SetStatementPanelController[V <: Value](
    override val view: SetStatementPanel,
    val valueController: ExpressionPanelController[Expression[V]])
        extends StatementPanelController[SetStatement[V]](view)

class GetPointerStatementPanelController[V <: Value](
    override val view: GetStatementPanel,
    val pointerController: PointerPanelController[V])
        extends GetStatementPanelController[V](view)
        with Logging {

    override def validate {
        // TODO check that the variable specified by the view is valid
        log.error("TODO: implement validate")
    }

    override def compile(scope: Scope): GetStatement[V] = {
        log.info("Compiling: Get TEXT");
        new GetStatement(pointerController.compile(scope))
    }
}

class SetPointerStatementPanelController[V <: Value](
    override val view: SetStatementPanel,
    val pointerController: PointerPanelController[V],
    override val valueController: ExpressionPanelController[Expression[V]])
        extends SetStatementPanelController[V](view, valueController)
        with Logging {

    override def validate {
        // TODO check that the variable specified by the view is valid
        log.error("TODO: implement validate")
    }

    override def compile(scope: Scope): SetStatement[V] = {
        log.info("Compiling: Set TEXT");
        new SetStatement(pointerController.compile(scope), valueController.compile(scope))
    }
}

class GetTextStatementPanelController(
    override val view: GetPointerStatementPanel,
    override val pointerController: PointerPanelController[Text])
        extends GetPointerStatementPanelController[Text](view, pointerController)

class SetTextPointerStatementPanelController(
    override val view: SetStatementPanel,
    override val pointerController: PointerPanelController[Text],
    override val valueController: TextPanelController)
        extends SetPointerStatementPanelController[Text](view, pointerController, valueController)

class SetNumberPointerStatementPanelController(
    override val view: SetStatementPanel,
    override val pointerController: PointerPanelController[Number],
    override val valueController: NumberPanelController)
        extends SetPointerStatementPanelController[Number](view, pointerController, valueController)

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

