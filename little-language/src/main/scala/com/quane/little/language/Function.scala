package com.quane.little.language

import scala.collection.mutable.ListBuffer
import com.quane.little.language.data.Value
import org.eintr.loglady.Logging
import com.google.common.base.Objects
import collection.mutable

/** Defines a function.
  *
  * @param name the function name
  */
class FunctionDefinition(val name: String)
  extends Scope {

  var scope: Scope = _
  private val _params = new ListBuffer[FunctionParameter]
  private[language] val block = new Block(this)

  def paramCount: Int = _params.length

  def stepCount: Int = block.length

  def addStep(step: Expression): FunctionDefinition = {
    block.addStep(step)
    this
  }

  def addParam(name: String): FunctionDefinition = {
    addParam(new FunctionParameter(name))
  }

  def addParam(param: FunctionParameter): FunctionDefinition = {
    _params += param
    this
  }

  def params: List[FunctionParameter] = _params.toList

  def steps: List[Expression] = block.steps

  def steps_=(steps: List[Expression]) = {
    block.clear()
    steps.foreach {
      step => block.addStep(step)
    }
  }

  override def toString: String = {
    Objects.toStringHelper(getClass)
      .add("name", name)
      .add("params", _params mkString("[", ",", "]"))
      .add("block", block)
      .toString
  }

}

/** Defines a function parameter to be specified at evaluation time.
  *
  * @param name the name of the parameter
  */
class FunctionParameter(val name: String) {

  override def toString = name

}

/** Reference to a [[com.quane.little.language.Block]].
  *
  * @param scope the scope in which the function is evaluated
  * @param name the name of the function
  */
class FunctionReference(var scope: Scope, val name: String)
  extends Expression
  with Scope
  with Logging {

  val args = mutable.Map[String, Expression]()

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

  override def equals(that: Any) = that match {
    case f: FunctionReference => name.equals(f.name) && args.equals(f.args)
    case _ => false
  }

  override def toString: String = {
    Objects.toStringHelper(getClass)
      .add("name", name)
      .add("args", args mkString("[", ",", "]"))
      .toString
  }

}

