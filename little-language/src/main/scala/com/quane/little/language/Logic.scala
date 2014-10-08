package com.quane.little.language

import com.quane.little.language.data.ValueType.ValueType
import com.quane.little.language.data.{ValueType, Value}
import com.google.common.base.Objects
import com.quane.little.language.LogicOperation.LogicOperation

/** An enumeration of all operations supported by the
  * [[com.quane.little.language.Logic]] expression.
  *
  * @author Sean Connolly
  */
object LogicOperation extends Enumeration {
  type LogicOperation = Value
  val Equals, NotEquals, LessThan, GreaterThan, And, Or = Value
}

/** A logical evaluation of the relationship between two arguments expressions.
  *
  * @param left the left operand
  * @param operation the evaluation operator
  * @param right the right operand
  *
  * @author Sean Connolly
  */
class Logic(val left: Code, val operation: LogicOperation, val right: Code) extends Code {

  override def evaluate(scope: Scope): Value = {
    val l = left.evaluate(scope)
    // TODO only evaluate the right if necessary
    val r = right.evaluate(scope)
    Value(
      operation match {
        case LogicOperation.Equals => l equals r
        case LogicOperation.NotEquals => !(l equals r)
        case LogicOperation.LessThan => (l compare r) < 0
        case LogicOperation.GreaterThan => (l compare r) > 0
        case LogicOperation.And => l.asBool && r.asBool
        case LogicOperation.Or => l.asBool || r.asBool
      }
    )
  }

  override def returnType(): ValueType = ValueType.Boolean

  override def equals(other: Any): Boolean = other match {
    case that: Logic =>
      that.isInstanceOf[Logic] &&
        operation == that.operation &&
        left == that.left &&
        right == that.right
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(left, operation, right)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("left", left)
      .add("operation", operation)
      .add("right", right)
      .toString

}
