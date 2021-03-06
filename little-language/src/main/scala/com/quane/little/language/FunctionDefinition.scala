package com.quane.little.language

import com.google.common.base.Objects
import com.quane.little.language.data.ValueType.ValueType
import com.quane.little.language.data.{Value, ValueType}

import scala.collection.immutable
import scala.collection.mutable.ListBuffer

/** Defines a function.<br/>
  * Note: a function definition _is not_ [[com.quane.little.language.Code]]
  *
  * @param name the function name
  * @param returnType the function's return type, defaults to [[com.quane.little.language.data.ValueType.Nothing]]
  * @see [[com.quane.little.language.FunctionReference]]
  * @see [[com.quane.little.language.FunctionParameter]]
  */
class FunctionDefinition(val name: String, var returnType: ValueType = ValueType.Nothing) {

  private val _params = new ListBuffer[FunctionParameter]
  private val block = new Block

  def paramCount: Int = _params.length

  def stepCount: Int = block.length

  def addStep(step: Code): FunctionDefinition = {
    block.addStep(step)
    this
  }

  def addParam(name: String, valueType: ValueType): FunctionDefinition =
    addParam(new FunctionParameter(name, valueType))

  def addParam(param: FunctionParameter): FunctionDefinition = {
    this += param
    this
  }

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

  def steps: List[Code] = block.steps

  def steps_=(steps: List[Code]) = block.steps = steps

  def asReference: FunctionReference = {
    val reference = new FunctionReference(name)
    params foreach {
      param =>
        // TODO parameters should have default values
        // TODO parameters' default values should be specific to their type
        reference.addArg(param.name, Value(""))
    }
    reference
  }

  /** Evaluate the function with the given `args`.
    *
    * The function defines its own [[Scope]] within that provided. In it, the
    * arguments are evaluated and set, and then the function's body is
    * evaluated. The output of the body is the return value of the function,
    * if any.
    *
    * @param scope the current scope in which to evaluate the
    * @param args the runtime arguments
    * @return the output of the function, or [[ValueType.Nothing]] otherwise
    */
  def evaluate(scope: Scope, args: immutable.Map[String, Code]): Value = {
    if (returnType != block.returnType) {
      throw new IllegalArgumentException(
        classOf[FunctionDefinition].getSimpleName + " '" + name + "' returns " + returnType
          + " but its " + classOf[Block].getSimpleName + " returns " + block.returnType + "."
      )
    }
    val functionScope = new Scope(scope)
    validateArguments(args)
    setArguments(functionScope, args)
    block.evaluate(functionScope)
  }

  private def validateArguments(args: immutable.Map[String, Code]) = {
    // TODO validate we have all arguments
    // TODO validate all arguments are appropriate for this definition
  }

  private def setArguments(scope: Scope, args: immutable.Map[String, Code]) = {
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