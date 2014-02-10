package com.quane.little.language

import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Map
import com.quane.little.language.data.Value
import org.eintr.loglady.Logging

/**
 * Defines a function.
 *
 * @param name the function name
 */
class FunctionDefinition(val name: String)
  extends Scope {

  var scope: Scope = _
  val params = new ListBuffer[String]
  val block = new Block(this)

  def addParam(name: String): FunctionDefinition = {
    addParam(new FunctionParameter(name))
  }

  def addParam(param: FunctionParameter): FunctionDefinition = {
    params += param.name
    this
  }

  def addStep(step: Expression): FunctionDefinition = {
    block.addStep(step)
    this
  }

}

/**
 * Defines a function parameter to be specified at evaluation time.
 *
 * @param name the name of the parameter
 */
class FunctionParameter(val name: String)

/**
 * Reference to a [[com.quane.little.language.Block]].
 *
 * @param scope the scope in which the function is evaluated
 * @param name the name of the function
 */
class FunctionReference(scope: Scope, val name: String)
  extends Block(scope)
  with Logging {

  private val args = Map[String, Expression]()

  def addArg(name: String, value: Expression): FunctionReference = {
    // TODO assert arg is not already set
    args(name) = value
    this
  }

  /**
   * Evaluate the referenced function.
   *
   * @return the function's return value
   */
  override def evaluate: Value = {
    log.info("Evaluating function '" + name + "'")
    val definition = fetchDefinition()
    setScope(definition)
    setArguments(definition)
    validateArguments(definition)
    evaluateDefinition(definition)
  }

  private def fetchDefinition(): FunctionDefinition = {
    scope.runtime.fetchFunction(name)
  }

  private def setScope(definition: FunctionDefinition) = {
    if (scope != definition) {
      // TODO is this correct? where to args go?
      definition.scope = scope
    }
  }

  private def validateArguments(definition: FunctionDefinition) = {
    // TODO Validate we have all arguments
  }

  private def setArguments(definition: FunctionDefinition) = {
    // TODO Set arguments in Scope (NON RECURSIVELY)
    args.foreach {
      case (argName, argValue) =>
        // TODO should we store args in the block's scope?
        definition.save(argName, argValue.evaluate)
    }
  }

  private def evaluateDefinition(definition: FunctionDefinition): Value = {
    definition.block.evaluate
  }

}

