package com.quane.little.ide.layout.language

import com.quane.little.language.{Expression, Scope, Function}
import org.eintr.loglady.Logging
import scala.collection.mutable.ListBuffer
import com.quane.little.ide.language.ExpressionController

/**
 *
 * @author Sean Connolly
 */
class FunctionPane
  extends ExpressionPane[Function]
  with Logging {

  val steps = new ListBuffer[ExpressionController[Expression[Any]]]()

  def compile(scope: Scope): Function = {
    log.info("Compiling: function..")
    val fun = new Function(scope) // TODO scope will always be null, yeah?
    steps.foreach(
      step =>
        fun.addStep(step.compile(fun))
    );
    fun
  }

}
