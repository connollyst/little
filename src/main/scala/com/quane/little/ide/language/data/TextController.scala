package com.quane.little.ide.language.data

import scala.swing.Reactor
import org.eintr.loglady.Logging
import com.quane.little.ide.StepAddedEvent
import com.quane.little.ide.language.ExpressionController
import com.quane.little.language.Scope
import com.quane.little.language.data.Text
import com.quane.little.language.Expression
import com.quane.little.ide.GetTextStatementAddedEvent

abstract class TextController(override val view: TextPanel)
        extends ExpressionController[Expression[Text]](view)

        with Logging {

    override def validate {
        log.error("TODO: implement validate")
    }
}

class TextFieldController(override val view: TextFieldPanel)
        extends TextController(view) {

    override def compile(scope: Scope): Expression[Text] = {
        val text = view.value
        return new Text(text)
    }
}

class TextExpressionController(override val view: TextExpressionPanel)
        extends TextController(view)
        with Reactor {

    var expression = None: Option[ExpressionController[Expression[Text]]]

    override def compile(scope: Scope): Expression[Text] = {
        expression.get.compile(scope);
    }

    listenTo(view)
    reactions += {
        case event: GetTextStatementAddedEvent =>
            expression = Option(event.controller)
        case event: StepAddedEvent =>
            log.error("Does not react to posted event: " + event)
    }
}
