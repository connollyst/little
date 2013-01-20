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

    val steps = new ListBuffer[ExpressionPanelController[Expression[Any]]]()

    def stepPanels: List[ExpressionPanelController[Expression[Any]]] = steps toList

    def addStep(controller: ExpressionPanelController[Expression[Any]]) = {
        steps += controller
    }

    override def validate: Unit = {
        // TODO
        log.error("TODO: implement FunctionPanelController.validate")
    }

    override def compile(scope: Scope): Function = {
        log.info("Compiling: function..")
        val fun = new Function
        stepPanels.foreach(
            step =>
                fun.addStep(step.compile(fun))
        );
        fun
    }

    listenTo(view)
    reactions += {
        case event: StepAddedEvent =>
            addStep(event.controller)
    }
}