package com.quane.little.ide.language.data

import scala.swing.Reactor

import org.eintr.loglady.Logging

import com.quane.little.ide.language.ExpressionPanelController
import com.quane.little.language.Scope
import com.quane.little.language.data.Bool

abstract class BoolPanelController(override val view: BoolPanel)
        extends ExpressionPanelController[Bool](view)
        with Reactor
        with Logging {

    override def validate {
        log.error("TODO: implement validate")
    }

}

class BoolFieldPanelController(override val view: BoolFieldPanel)
        extends BoolPanelController(view) {

    override def compile(scope: Scope): Bool = {
        val num = view.value
        return new Bool(num)
    }

}

class BoolExpressionPanelController(override val view: BoolExpressionPanel)
        extends BoolPanelController(view) {

    override def compile(scope: Scope): Bool = {
        // TODO get Expression
        return new Bool(false)
    }
}
