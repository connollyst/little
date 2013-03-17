package com.quane.little.ide.language.data

import scala.swing.Reactor

import org.eintr.loglady.Logging

import com.quane.little.ide.language.ExpressionController
import com.quane.little.language.Scope
import com.quane.little.language.data.Bool

abstract class BoolController(override val view: BoolPanel)
        extends ExpressionController[Bool](view)
        with Reactor
        with Logging {

    override def validate {
        log.error("TODO: implement validate")
    }

}

// TODO the BoolFieldPanel controller and BoolExpressionController should be combined!?!??!!!!

class BoolFieldController(override val view: BoolFieldPanel)
        extends BoolController(view) {

    override def compile(scope: Scope): Bool = {
        val num = view.value
        return new Bool(num)
    }

}

class BoolExpressionController(override val view: BoolExpressionPanel)
        extends BoolController(view) {

    override def compile(scope: Scope): Bool = {
        // TODO get Expression
        return new Bool(false)
    }
}
