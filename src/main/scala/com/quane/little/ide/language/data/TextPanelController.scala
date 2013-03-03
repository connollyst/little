package com.quane.little.ide.language.data

import scala.swing.Reactor
import org.eintr.loglady.Logging
import com.quane.little.language.Scope
import com.quane.little.language.data.Text
import com.quane.little.ide.language.ExpressionPanelController

abstract class TextPanelController(override val view: TextPanel)
        extends ExpressionPanelController[Text](view)
        with Reactor
        with Logging {

    override def validate {
        log.error("TODO: implement validate")
    }

}

class TextFieldPanelController(override val view: TextFieldPanel)
        extends TextPanelController(view) {

    override def compile(scope: Scope): Text = {
        val text = view.value
        return new Text(text)
    }

}

class TextExpressionPanelController(override val view: TextExpressionPanel)
        extends TextPanelController(view) {

    override def compile(scope: Scope): Text = {
        // TODO get Expression
        return new Text("todo")
    }
}
