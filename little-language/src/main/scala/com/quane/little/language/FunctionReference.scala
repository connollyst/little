package com.quane.little.language

import com.google.common.base.Objects
import com.quane.little.language.data.ValueType.ValueType
import com.quane.little.language.data.{ValueType, Value}

import scala.collection.mutable

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
    if (args.contains(name)) throw new IllegalAccessException(
      "Tried to change argument '" + name + "' from '" + args(name) + "' to '" + value + "'."
    )
    // TODO validate arg valueType
    args(name) = value
    this
  }

  // TODO depends on FunctionDefinition
  override def returnType: ValueType = ValueType.Something

  /**
   * Evaluate the referenced function.
   *
   * @return the function's return value
   */
  override def evaluate(scope: Scope): Value =
    scope.runtime.fetchFunction(name).evaluate(scope, args.toMap)

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

