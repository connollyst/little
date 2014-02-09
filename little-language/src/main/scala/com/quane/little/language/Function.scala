package com.quane.little.language

import scala.collection.mutable.ListBuffer
import com.quane.little.language.data.{ValueType, Value}

/** Defines a function.
  *
  * @param name
  */
class FunctionDefinition(val name: String)
  extends Scope {

  var scope: Scope = new Scope {
    var scope: Scope = _
  }
  val params: ListBuffer[FunctionParameter] = new ListBuffer[FunctionParameter]
  val block: Block = new Block(this)

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

/** Defines a function parameter to be specified at evaluation time.
  *
  * @param name the name of the parameter
  * @param paramType the type of the parameter
  */
class FunctionParameter(val name: String, val paramType: ValueType)

/** Reference to a [[com.quane.little.language.Block]].
  *
  * @param scope the scope in which the function is evaluated
  * @param name the name of the function
  */
class FunctionReference(scope: Scope, val name: String)
  extends Block(scope) {

  override def evaluate: Value = {
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

