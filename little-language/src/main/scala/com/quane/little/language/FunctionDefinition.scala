package com.quane.little.language

import scala.collection.mutable.ListBuffer
import com.quane.little.language.data.Value
import scala.collection.immutable
import com.google.common.base.Objects

/** Defines a function.
  *
  * @param name the function name
  */
class FunctionDefinition(val name: String) {

  private val _params = new ListBuffer[FunctionParameter]
  private val block = new Block

  def paramCount: Int = _params.length

  def stepCount: Int = block.length

  def addStep(step: EvaluableCode): FunctionDefinition = {
    block.addStep(step)
    this
  }

  def addParam(name: String): FunctionDefinition = {
    this += name
    this
  }

  def addParam(param: FunctionParameter): FunctionDefinition = {
    this += param
    this
  }

  def +=(name: String) = _params += new FunctionParameter(name)

  def +=(param: FunctionParameter) = _params += param

  def params: List[FunctionParameter] = _params.toList

  def params_=(params: List[FunctionParameter]) = {
    _params.clear()
    params.foreach {
      param => _params += param
    }
    // TODO why does this cause tests to fail?..:
    // _params ++= params
  }

  def steps: List[EvaluableCode] = block.steps

  def steps_=(steps: List[EvaluableCode]) = block.steps = steps

  def asReference: FunctionReference = {
    val reference = new FunctionReference(name)
    params foreach {
      param =>
        // TODO parameters should have default values
        reference.addArg(param.name, Value(""))
    }
    reference
  }

  def evaluate(scope: Scope, args: immutable.Map[String, Expression]): Value = {
    val functionScope = new Scope(scope)
    validateArguments(args)
    setArguments(functionScope, args)
    block.evaluate(functionScope)
  }

  private def validateArguments(args: immutable.Map[String, Expression]) = {
    // TODO validate we have all arguments
    // TODO validate all arguments are appropriate for this definition
  }

  private def setArguments(scope: Scope, args: immutable.Map[String, Expression]) = {
    // TODO Set arguments in Scope (NON RECURSIVELY)
    args.foreach {
      case (argName, argValue) =>
        scope.save(argName, argValue.evaluate(scope))
    }
  }

  override def equals(other: Any): Boolean = other match {
    case that: FunctionDefinition =>
      name == that.name &&
        params == that.params &&
        block == that.block
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(name, params, block)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("name", name)
      .add("params", _params mkString("[", ",", "]"))
      .add("block", block)
      .toString

}

/** Defines a function parameter to be specified at evaluation time.
  *
  * @param name the name of the parameter
  */
class FunctionParameter(val name: String) {

  override def toString = name

}