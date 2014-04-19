package com.quane.little.language

import collection.immutable
import collection.mutable
import com.google.common.base.Objects
import com.quane.little.language.data.Value
import scala.collection.mutable.ListBuffer

/** Defines a function.
  *
  * @param name the function name
  */
class FunctionDefinition(val name: String) {

  private val _params = new ListBuffer[FunctionParameter]
  private val block = new Block

  def paramCount: Int = _params.length

  def stepCount: Int = block.length

  def addStep(step: Expression): FunctionDefinition = {
    block.addStep(step)
    this
  }

  def addParam(name: String): FunctionDefinition =
    addParam(new FunctionParameter(name))

  def addParam(param: FunctionParameter): FunctionDefinition = {
    _params += param
    this
  }

  def params: List[FunctionParameter] = _params.toList

  def params_=(params: List[FunctionParameter]) = {
    _params.clear()
    params.foreach {
      param => addParam(param)
    }
  }

  def steps: List[Expression] = block.steps

  def steps_=(steps: List[Expression]) = {
    block.clear()
    steps.foreach {
      step => block.addStep(step)
    }
  }

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

/** Reference to a [[com.quane.little.language.Block]].
  *
  * @param name the name of the function
  */
class FunctionReference(val name: String)
  extends Expression {

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
  override def evaluate(scope: Scope): Value = {
    val definition = scope.runtime.fetchFunction(name)
    definition.evaluate(scope, args.toMap)
  }

  override def equals(that: Any) = that match {
    case f: FunctionReference => name.equals(f.name) && args.equals(f.args)
    case _ => false
  }

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("name", name)
      .add("args", args mkString("[", ",", "]"))
      .toString

}

