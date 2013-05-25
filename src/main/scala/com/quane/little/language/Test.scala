package com.quane.little.language

import com.quane.little.language.data.Value

class Test(left: Expression[Value],
           operator: LogicalOperator,
           right: Expression[Value])
  extends Expression[Boolean] {

  def evaluate: Boolean = isTrue

  def isTrue: Boolean = {
    operator match {
      case AND => {
        (left.evaluate.asBool && right.evaluate.asBool)
      }
      case OR => {
        (left.evaluate.asBool || right.evaluate.asBool)
      }
    }
  }

  def isFalse: Boolean = !isTrue

}

sealed trait LogicalOperator

object AND extends LogicalOperator

object OR extends LogicalOperator

// TODO XOR etc?