package com.quane.little.ide.language.statement

import org.eintr.loglady.Logging

import com.quane.little.ide.language.memory.PointerPanelController
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
abstract class GetStatementPanelController[V <: Value](
    override val view: GetStatementPanel)
        extends StatementPanelController[GetStatement[V]](view)

abstract class GetPointerStatementPanelController[V <: Value](
    override val view: GetStatementPanel,
    val pointerController: PointerPanelController[V])
        extends GetStatementPanelController[V](view)
        with Logging {

    override def validate {
        // TODO check that the variable specified by the view is valid
        log.error("TODO: implement validate")
    }
}

class GetBoolStatementPanelController(
    override val view: GetPointerStatementPanel,
    override val pointerController: PointerPanelController[Bool])
        extends GetPointerStatementPanelController[Bool](view, pointerController) {

    override def compile(scope: Scope): GetStatement[Bool] = {
        log.info("Compiling: Get BOOL");
        new GetBoolStatement(pointerController.compile(scope))
    }
}

class GetNumberStatementPanelController(
    override val view: GetPointerStatementPanel,
    override val pointerController: PointerPanelController[Number])
        extends GetPointerStatementPanelController[Number](view, pointerController) {

    override def compile(scope: Scope): GetStatement[Number] = {
        log.info("Compiling: Get NUMBER");
        new GetNumberStatement(pointerController.compile(scope))
    }
}
class GetTextStatementPanelController(
    override val view: GetPointerStatementPanel,
    override val pointerController: PointerPanelController[Text])
        extends GetPointerStatementPanelController[Text](view, pointerController) {

    override def compile(scope: Scope): GetStatement[Text] = {
        log.info("Compiling: Get TEXT");
        new GetTextStatement(pointerController.compile(scope))
    }
}
