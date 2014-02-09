package com.quane.little.language

import scala.collection.mutable.ListBuffer
import com.quane.little.language.data.{ValueType, Value}

/** Reference to a [[com.quane.little.language.Block]].
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
      // TODO is this correct? where to args go?
      function.scope = scope
    }
    // TODO Validate we have all arguments
    // TODO Evaluate all arguments to Values
    // TODO Set arguments in Scope (NON RECURSIVELY)
    // TODO Evaluate Block
    function.block.evaluate
  }

}

class FunctionDefinition(var scope: Scope, val name: String)
  extends Scope {

  val runtime: Runtime = scope.runtime
  val params: ListBuffer[FunctionParameter] = new ListBuffer[FunctionParameter]
  val block: Block = new Block(scope)

  def addParam(name: String, paramType: ValueType): FunctionDefinition = {
    addParam(new FunctionParameter(name, paramType))
  }

  def addParam(param: FunctionParameter): FunctionDefinition = {
    params += param
    this
  }

  def addStep(step: Expression): FunctionDefinition = {
    block.addStep(step)
    this
  }

}

class FunctionParameter(val name: String, val paramType: ValueType)