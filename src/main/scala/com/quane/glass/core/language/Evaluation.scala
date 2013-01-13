package com.quane.glass.core.language

class Evaluation[T](
    left: Expression[T],
    operator: EvaluationOperator,
    right: Expression[T])
        extends Expression[Boolean] {

    def evaluate: Boolean = {
        operator match {
            case Equals => {
                left equals right
            }
            case NotEquals => {
                !(left equals right)
            }
        }
    }
}

sealed trait EvaluationOperator
object Equals extends EvaluationOperator
object NotEquals extends EvaluationOperator