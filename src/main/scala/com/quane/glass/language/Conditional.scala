package com.quane.glass.language

import com.quane.glass.language.data.Bool


/** A conditional statement is an {@code Expression} which evaluates a
  * {@code Function} if the test, an {@code Expression} itself, evaluates to
  * {@value true}.
  *
  * @author Sean Connolly
  */
class Conditional(test: Expression[Bool], function: Function)
        extends Expression[Boolean] {

    /** Evaluate the conditional statement; only if the <i>test</i> evaluates
      * to {@code true}, the <i>function</i> is evaluated.
      */
    def evaluate: Boolean = {
        if (test.evaluate.primitive) {
            function.evaluate
            true
        } else {
            false
        }
    }

}