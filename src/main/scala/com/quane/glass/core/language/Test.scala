package com.quane.glass.core.language

class Test(
    var left: Expression[Boolean],
    var operator: Operator,
    var right: Expression[Boolean])
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

sealed trait Operator
object AND extends Operator
object OR extends Operator