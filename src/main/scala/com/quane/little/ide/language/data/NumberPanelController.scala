package com.quane.little.ide.language.data

import scala.swing.Reactor

import org.eintr.loglady.Logging

import com.quane.little.ide.language.ExpressionController
import com.quane.little.language.Scope
import com.quane.little.language.data.Number

abstract class NumberController(override val view: NumberPanel)
        extends ExpressionController[Number](view)
        with Reactor
        with Logging {

    override def validate {
        log.error("TODO: implement validate")
    }

}

class NumberFieldController(override val view: NumberFieldPanel)
        extends NumberController(view) {

    override def compile(scope: Scope): Number = {
        val num = view.value
        return new Number(num)
    }

}

class NumberExpressionController(override val view: NumberExpressionPanel)
        extends NumberController(view) {

    override def compile(scope: Scope): Number = {
        // TODO get Expression
        return new Number(137)
    }
}
