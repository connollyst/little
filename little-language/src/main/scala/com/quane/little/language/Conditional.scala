package com.quane.little.language

import com.quane.little.language.data.Value


/** A conditional statement is an {@code Expression} which evaluates a
  * {@code Function} if the test, an {@code Expression} itself, evaluates to
  * {@value true}.
  *
  * @author Sean Connolly
  */
class Conditional(test: Expression[Value], function: Function)
  extends Expression[Boolean] {

  /** Evaluate the conditional statement; only if the <i>test</i> evaluates
    * to {@code true}, the <i>function</i> is evaluated.
    */
  def evaluate: Boolean = {
    if (test.evaluate.asBool) {
      function.evaluate
      true
    } else {
      false
    }
  }

}