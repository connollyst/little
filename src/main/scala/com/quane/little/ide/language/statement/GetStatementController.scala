package com.quane.little.ide.language.statement

import org.eintr.loglady.Logging

import com.quane.little.ide.language.memory.PointerController
import com.quane.little.language.GetBoolStatement
import com.quane.little.language.GetNumberStatement
import com.quane.little.language.GetStatement
import com.quane.little.language.GetTextStatement
import com.quane.little.language.Scope
import com.quane.little.language.data.Bool
import com.quane.little.language.data.Text
import com.quane.little.language.data.Value
import com.quane.little.language.data.Number

/** The controller for the [[GetStatementPanel]] is responsible for creating a
  * [[GetStatement]] as represented by the view.
  *
  * @author Sean Connolly
  */
abstract class GetStatementController[V <: Value](
    override val view: GetStatementPanel)
        extends StatementController[GetStatement[V]](view)

abstract class GetPointerStatementController[V <: Value](
    override val view: GetStatementPanel,
    val pointerController: PointerController[V])
        extends GetStatementController[V](view)
        with Logging {

    override def validate {
        // TODO check that the variable specified by the view is valid
        log.error("TODO: implement validate")
    }
}

class GetBoolStatementController(
    override val view: GetPointerStatementPanel,
    override val pointerController: PointerController[Bool])
        extends GetPointerStatementController[Bool](view, pointerController) {

    override def compile(scope: Scope): GetStatement[Bool] = {
        log.info("Compiling: Get BOOL");
        new GetBoolStatement(pointerController.compile(scope))
    }
}

class GetNumberStatementController(
    override val view: GetPointerStatementPanel,
    override val pointerController: PointerController[Number])
        extends GetPointerStatementController[Number](view, pointerController) {

    override def compile(scope: Scope): GetStatement[Number] = {
        log.info("Compiling: Get NUMBER");
        new GetNumberStatement(pointerController.compile(scope))
    }
}
class GetTextStatementController(
    override val view: GetPointerStatementPanel,
    override val pointerController: PointerController[Text])
        extends GetPointerStatementController[Text](view, pointerController) {

    override def compile(scope: Scope): GetStatement[Text] = {
        log.info("Compiling: Get TEXT");
        new GetTextStatement(pointerController.compile(scope))
    }
}
