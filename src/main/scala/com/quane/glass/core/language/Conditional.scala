package com.quane.glass.core.language

/** A conditional statement is an {@code Expression} which evaluates a
  * {@code Function} if the test, an {@code Expression} itself, evaluates to
  * {@value true}.
  *
  * @author Sean Connolly
  */
class Conditional(var test: Expression[Boolean], var function: Function)
        extends Expression[Unit] {

    /** Evaluate the conditional statement; only if the <i>test</i> evaluates
      * to {@code true}, the <i>function</i> is evaluated.
      */
    def evaluate: Unit = if (test.evaluate) { function.evaluate }

}