package com.quane.little.language

import com.quane.little.language.data.Value

class Evaluation[T](left: Expression[T],
                    operator: EvaluationOperator,
                    right: Expression[T])
  extends Expression[Value] {

  def evaluate: Value = {
    // TODO clean this up, gross!
    val retVal = operator match {
      case Equals => {
        left equals right
      }
      case NotEquals => {
        !(left equals right)
      }
    }
    new Value(retVal)
  }
}

sealed trait EvaluationOperator

object Equals extends EvaluationOperator

object NotEquals extends EvaluationOperator

// TODO GreaterThan, LessThan, Contains..