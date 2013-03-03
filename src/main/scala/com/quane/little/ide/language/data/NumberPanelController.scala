package com.quane.little.ide.language.data

import scala.swing.Reactor
import org.eintr.loglady.Logging
import com.quane.little.language.Scope
import com.quane.little.language.data.Number
import com.quane.little.ide.language.ExpressionPanelController

abstract class NumberPanelController(override val view: NumberPanel)
        extends ExpressionPanelController[Number](view)
        with Reactor
        with Logging {

    override def validate {
        log.error("TODO: implement validate")
    }

}

class NumberFieldPanelController(override val view: NumberFieldPanel)
        extends NumberPanelController(view) {

    override def compile(scope: Scope): Number = {
        val num = view.value
        return new Number(num)
    }

}

class NumberExpressionPanelController(override val view: NumberExpressionPanel)
        extends NumberPanelController(view) {

    override def compile(scope: Scope): Number = {
        // TODO get Expression
        return new Number(137)
    }
}
