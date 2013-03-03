package com.quane.little.ide.language

import scala.swing.Reactor

import org.eintr.loglady.Logging

import com.quane.little.language.Scope
import com.quane.little.language.data.Direction

abstract class DirectionPanelController(override val view: DirectionPanel)
        extends ExpressionPanelController[Direction](view)
        with Reactor
        with Logging {

    override def validate {
        log.error("TODO: implement validate")
    }

}

class DirectionFieldPanelController(override val view: DirectionFieldPanel)
        extends DirectionPanelController(view) {

    override def compile(scope: Scope): Direction = {
        val num = view.value
        return new Direction(num)
    }

}

class DirectionExpressionPanelController(override val view: DirectionExpressionPanel)
        extends DirectionPanelController(view) {

    override def compile(scope: Scope): Direction = {
        // TODO get Expression
        return new Direction(137)
    }
}
