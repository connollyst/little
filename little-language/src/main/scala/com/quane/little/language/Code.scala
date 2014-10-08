package com.quane.little.language

import com.google.common.base.Objects
import com.quane.little.language.data.{Value, ValueType}
import com.quane.little.language.data.ValueType._

/** Code is the root of the little language.
  *
  * @author Sean Connolly
  */
trait Code {

  override def toString: String = Objects.toStringHelper(getClass).toString

}

trait EvaluableCode extends Code {
  // TODO is there any non-evaluable code??

  /** Returns this expression's [[com.quane.little.language.data.ValueType]]
    *
    * @return the return value type
    */
  def returnType: ValueType

  /** Evaluate this code, in the given [[com.quane.little.language.Scope]],
    * and output it's return [[com.quane.little.language.data.Value]], if any.
    *
    * @param scope the scope in which to evaluate the expression
    * @return the return value of the expression
    */
  def evaluate(scope: Scope): Value

}