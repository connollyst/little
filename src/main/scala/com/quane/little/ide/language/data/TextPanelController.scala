package com.quane.little.ide.language.data

import scala.swing.Reactor
import org.eintr.loglady.Logging
import com.quane.little.ide.StepAddedEvent
import com.quane.little.ide.language.ExpressionPanelController
import com.quane.little.language.Scope
import com.quane.little.language.data.Text
import com.quane.little.language.Expression
import com.quane.little.ide.GetTextStatementAddedEvent

abstract class TextPanelController(override val view: TextPanel)
        extends ExpressionPanelController[Expression[Text]](view)
        with Reactor
        with Logging {

    override def validate {
        log.error("TODO: implement validate")
    }
}

class TextFieldPanelController(override val view: TextFieldPanel)
        extends TextPanelController(view) {

    override def compile(scope: Scope): Expression[Text] = {
        val text = view.value
        return new Text(text)
    }
}

class TextExpressionPanelController(override val view: TextExpressionPanel)
        extends TextPanelController(view) {

    var expression = None: Option[ExpressionPanelController[Expression[Text]]]

    override def compile(scope: Scope): Expression[Text] = {
        expression.get.compile(scope);
    }

    listenTo(view)
    reactions += {
        case event: GetTextStatementAddedEvent =>
            expression = Option(event.controller)
        case _ =>
            log.error(getClass().getSimpleName + " does not react to posted event.")
    }
}
