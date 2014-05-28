package com.quane.little.language

import collection.mutable
import com.google.common.base.Objects
import com.quane.little.language.data.Value

/** A reference to a [[com.quane.little.language.FunctionDefinition]] for
  * evaluation.
  *
  * @param name the name of the function
  * @see [[com.quane.little.language.FunctionDefinition]]
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

