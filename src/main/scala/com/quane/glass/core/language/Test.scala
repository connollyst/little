package com.quane.glass.core.language

class Test(
    left: Expression[Boolean],
    operator: LogicalOperator,
    right: Expression[Boolean])
        extends Expression[Boolean] {

    def evaluate: Boolean = isTrue

    def isTrue: Boolean = {
        operator match {
            case AND => {
                (left.evaluate && right.evaluate)
            }
            case OR => {
                (left.evaluate || right.evaluate)
            }
        }
    }

    def isFalse: Boolean = !isTrue

}

sealed trait LogicalOperator
object AND extends LogicalOperator
object OR extends LogicalOperator