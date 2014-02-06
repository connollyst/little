package com.quane.little.language

import com.quane.little.language.data.Value

/** A logical test of boolean values.
  *
  * @param left the left operand
  * @param operator the test operator
  * @param right the right operand
  */
class Test(left: Expression[_ <: Value],
           operator: LogicalOperator,
           right: Expression[_ <: Value])
  extends Expression[Value] {

  def evaluate: Value = new Value(isTrue)

  def isTrue: Boolean = {
    val l = left.evaluate.asBool
    val r = right.evaluate.asBool
    operator match {
      case AND => l && r
      case OR => l || r
    }
  }

  def isFalse: Boolean = !isTrue

}

sealed trait LogicalOperator

object AND extends LogicalOperator

object OR extends LogicalOperator

// TODO XOR etc?