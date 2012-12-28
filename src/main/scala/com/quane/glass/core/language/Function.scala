package com.quane.glass.core.language

/** A Function is a collection of steps which are evaluated in sequence when
  * the Function itself is evaluated.<br/>
  * A Function is a type of {@code Block} and, as such, has a {@code Scope}.
  * Thus, any variables defined within are defined only in it's scope.
  *
  * @param steps
  */
class Function(override val scope: Scope, var steps: List[_ <: Statement[_ <: Any]])
        extends Block(scope) {

    def this(scope: Scope) = this(scope, List[Statement[_ <: Any]]())

    def addStep(step: Statement[_ <: Any]): Unit = {
        steps = steps ::: List(step)
    }

    def evaluate: Any = {
        steps.foreach(step => step.evaluate)
    }

}