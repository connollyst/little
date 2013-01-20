package com.quane.glass.ide.language

import org.eintr.loglady.Logging
import com.quane.glass.core.language.Function
import com.quane.glass.core.language.Scope
import scala.swing.Reactor
import com.quane.glass.ide.StepAddedEvent
import scala.collection.mutable.ListBuffer
import com.quane.glass.core.language.Expression

class FunctionPanelController(view: FunctionPanel)
        extends ExpressionPanelController[Function](view)
        with Reactor
        with Logging {

    log.info("Creating a FunctionPanelController")

    val steps = new ListBuffer[ExpressionPanelController[Expression[Any]]]()

    override def validate: Unit = {
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