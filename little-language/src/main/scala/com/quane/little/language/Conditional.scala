package com.quane.little.language

import com.quane.little.language.data.{Nada, Value}


/** A conditional is an [[com.quane.little.language.Expression]] which evaluates
  * a [[com.quane.little.language.Function]] if its test evaluates to {@code true}.
  *
  * @author Sean Connolly
  */
class Conditional(test: Expression[_ <: Value], function: Function)
  extends Expression[Nada] {

  /** Evaluate the conditional statement; only if the <i>test</i> evaluates
    * to {@code true}, the <i>function</i> is evaluated.
    */
  def evaluate: Nada = {
    if (test.evaluate.asBool) {
      function.evaluate
    }
    new Nada
  }

}