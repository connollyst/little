package com.quane.little.language.math

import com.google.common.base.Objects
import com.quane.little.language.data.Value
import com.quane.little.language.math.BasicMathOperation.BasicMathOperation
import com.quane.little.language.{Code, Scope}

/** Factory for [[com.quane.little.language.math.BasicMath]] instances. **/
object BasicMath {

  /** Returns an appropriate [[com.quane.little.language.math.BasicMath]]
    * expression as specified by the given
    * [[com.quane.little.language.math.BasicMathOperation]].
    *
    * @param l the left operand
    * @param o the operator
    * @param r the right operand
    * @return the math expression
    */
  def apply(l: Code, o: BasicMathOperation, r: Code): BasicMath = {
    o match {
      case BasicMathOperation.Add => new Addition(l, r)
      case BasicMathOperation.Subtract => new Subtraction(l, r)
      case BasicMathOperation.Multiply => new Multiplication(l, r)
      case BasicMathOperation.Divide => new Division(l, r)
    }
  }

}

/** A basic math operation. **/
object BasicMathOperation extends Enumeration {
  type BasicMathOperation = Value
  val Add, Subtract, Multiply, Divide = Value
}


/** An abstract basic math expression.
  *
  * @param l the left operand
  * @param r the right operand
  */
abstract class BasicMath(val l: Code, val r: Code) extends Math {

  override def evaluate(scope: Scope): Value = {
    val left = l.evaluate(scope)
    val right = r.evaluate(scope)
    evaluate(left, right)
  }

  def operation: BasicMathOperation

  def evaluate(left: Value, right: Value): Value

  override def equals(other: Any): Boolean = other match {
    case that: BasicMath if that.getClass == getClass =>
      l == that.l && r == that.r
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(l, r)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("l", l)
      .add("r", r)
      .toString

}

/** A basic addition expression.
  *
  * @param l the left operand
  * @param r the right operand
  */
class Addition(l: Code, r: Code) extends BasicMath(l, r) {

  // TODO only required for Jackson deserialization, can we avoid?
  def this() = this(null, null)

  override def operation = BasicMathOperation.Add

  override def evaluate(left: Value, right: Value): Value =
    Value(left.asDouble + right.asDouble)

}

/** A basic subtraction expression.
  *
  * @param l the left operand
  * @param r the right operand
  */
class Subtraction(l: Code, r: Code) extends BasicMath(l, r) {

  // TODO only required for Jackson deserialization, can we avoid?
  def this() = this(null, null)

  override def operation = BasicMathOperation.Subtract

  override def evaluate(left: Value, right: Value): Value =
    Value(left.asDouble - right.asDouble)

}

/** A basic multiplication expression.
  *
  * @param l the left operand
  * @param r the right operand
  */
class Multiplication(l: Code, r: Code) extends BasicMath(l, r) {

  // TODO only required for Jackson deserialization, can we avoid?
  def this() = this(null, null)

  override def operation = BasicMathOperation.Multiply

  override def evaluate(left: Value, right: Value): Value =
    Value(left.asDouble * right.asDouble)

}

/** A basic division expression.
  *
  * @param l the left operand
  * @param r the right operand
  */
class Division(l: Code, r: Code) extends BasicMath(l, r) {

  // TODO only required for Jackson deserialization, can we avoid?
  def this() = this(null, null)

  override def operation = BasicMathOperation.Divide

  override def evaluate(left: Value, right: Value): Value =
    Value(left.asDouble / right.asDouble)

}