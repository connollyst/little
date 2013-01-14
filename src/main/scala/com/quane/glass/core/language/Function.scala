package com.quane.glass.core.language

import scala.collection.mutable.ListBuffer

/** A Function is a collection of steps which are evaluated in sequence when
  * the Function itself is evaluated.<br/>
  * A Function is a type of {@code Block} and, as such, has a {@code Scope}.
  * Thus, any variables defined within are defined only in it's scope.
  *
  * @param steps
  */
class Function(scope: Scope, steps: ListBuffer[Expression[Any]])
        extends Block(scope) {

    /** Alternative constructor; create an empty function.
      */
    def this(scope: Scope) = this(scope, ListBuffer[Expression[Any]]())

    def this() = this(null) // TODO we should avoid null, yeah?

    def addStep(step: Expression[Any]): Unit = {
        steps += step
    }

    def evaluate: Any = {
        steps.foreach(step => step.evaluate)
    }

}