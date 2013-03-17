package com.quane.little.ide.language.math

import scala.swing.Reactor
import org.eintr.loglady.Logging
import com.quane.little.ide.language.ExpressionPanelController
import com.quane.little.language.Scope
import com.quane.little.language.math.Addition
import com.quane.little.language.Expression
import com.quane.little.language.math.BasicMath

class MathPanelController(override val view: MathPanel)
        extends ExpressionPanelController[BasicMath](view)
        with Reactor
        with Logging {

    override def validate {
        log.error("TODO: implement validate")
    }
    
    override def compile(scope: Scope): BasicMath = {
        val expressionA = view.panelA.compile(scope)
        val expressionB = view.panelB.compile(scope)
        new Addition(expressionA, expressionB)
    }

}