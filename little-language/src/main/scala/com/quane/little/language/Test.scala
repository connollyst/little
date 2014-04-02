package com.quane.little.language

import com.quane.little.language.data.Value

/** A logical test of boolean values.
  *
  * @param left the left operand
  * @param operator the test operator
  * @param right the right operand
  */
class Test(left: Expression,
           operator: LogicalOperator,
           right: Expression)
  extends Expression {

  override def evaluate(scope: Scope): Value = Value(isTrue(scope))

  def isTrue(scope: Scope): Boolean = {
    val l = left.evaluate(scope).asBool
    val r = right.evaluate(scope).asBool
    operator match {
      case AND => l && r
      case OR => l || r
    }
  }

  def isFalse(scope: Scope): Boolean = !isTrue(scope)

}

sealed trait LogicalOperator

object AND extends LogicalOperator

object OR extends LogicalOperator

// TODO XOR etc?