package com.quane.little.language

import scala.collection.mutable.ListBuffer
import com.quane.little.language.data.{Value, Nada}

/** A function is a collection of steps which are evaluated in sequence when
  * the function itself is evaluated.
  *
  * A function is a type of [[com.quane.little.language.Block]] and, as such,
  * has a [[com.quane.little.language.Scope]]. Thus, any variables defined
  * within are limited to this scope.
  */
class Function(var scope: Scope, steps: ListBuffer[Expression])
  extends Block(scope) {

  val runtime: Runtime = scope.runtime
  var lastStep: Expression = new Nada

  def this(scope: Scope) = this(scope, ListBuffer[Expression]())

  def addStep(step: Expression): Function = {
    steps += step
    this
  }

  def evaluate: Value = {
    steps.foreach(step => step.evaluate)
    lastStep.evaluate
  }

}

/** Reference to a [[com.quane.little.language.Function]].
  *
  * @param scope the scope from which the function can be retrieved
  * @param name the name of the function
  */
class FunctionReference(val scope: Scope, val name: String)
  extends Expression {

  /** Evaluate the referenced function within the current
    * [[com.quane.little.language.Scope]].
    *
    * @return the result of the function evaluation
    */
  def evaluate: Value = {
    val function = scope.runtime.fetchFunction(name)
    if (scope != function) {
      function.scope = scope
    }
    function.evaluate
  }

}