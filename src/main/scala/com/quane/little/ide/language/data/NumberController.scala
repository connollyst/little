package com.quane.little.ide.language.data

import scala.swing.Reactor

import org.eintr.loglady.Logging

import com.quane.little.ide.language.ExpressionController
import com.quane.little.language.Expression
import com.quane.little.language.Scope
import com.quane.little.language.data.Number

abstract class NumberController(override val view: NumberPanel)
  extends ExpressionController[Expression[Number]](view)
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
  extends NumberController(view)
  with Reactor {

  var expression = None: Option[ExpressionController[Expression[Number]]]

  override def compile(scope: Scope): Expression[Number] = {
    expression.get.compile(scope);
  }

}
