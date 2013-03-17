package com.quane.little.ide.language

import org.eintr.loglady.Logging
import com.quane.little.language.Function
import com.quane.little.language.Scope
import scala.swing.Reactor
import com.quane.little.ide.StepAddedEvent
import scala.collection.mutable.ListBuffer
import com.quane.little.language.Expression

class FunctionController(override val view: FunctionPanel)
        extends ExpressionController[Function](view)
        with Reactor
        with Logging {

    val steps = new ListBuffer[ExpressionController[Expression[Any]]]()

    override def validate {
        log.error("TODO: implement validate")
    }

    override def compile(scope: Scope): Function = {
        log.info("Compiling: function..")
        val fun = new Function(scope) // TODO scope will always be null, yeah?
        steps.foreach(
            step =>
                fun.addStep(step.compile(fun))
        );
        fun
    }

    listenTo(view)
    reactions += {
        case event: StepAddedEvent =>
            steps += event.controller
    }
}