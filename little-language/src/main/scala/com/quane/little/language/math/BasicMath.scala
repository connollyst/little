package com.quane.little.language.math

import com.quane.little.language.{Scope, Expression}
import com.quane.little.language.data.Value
import com.google.common.base.Objects

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
  def apply(l: Expression, o: BasicMathOperation, r: Expression): BasicMath = {
    o match {
      case _: AdditionOperation => new Addition(l, r)
      case _: SubtractionOperation => new Subtraction(l, r)
      case _: MultiplicationOperation => new Multiplication(l, r)
      case _: DivisionOperation => new Division(l, r)
    }
  }

}

/** A basic math operation. **/
sealed trait BasicMathOperation

class AdditionOperation extends BasicMathOperation

class SubtractionOperation extends BasicMathOperation

class MultiplicationOperation extends BasicMathOperation

class DivisionOperation extends BasicMathOperation

/** An abstract basic math expression.
  *
  * @param l the left operand
  * @param r the right operand
  */
abstract class BasicMath(val l: Expression, val r: Expression) extends Math {

  override def evaluate(scope: Scope): Value = {
    val left = l.evaluate(scope)
    val right = r.evaluate(scope)
    evaluate(left, right)
  }

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
class Addition(l: Expression, r: Expression) extends BasicMath(l, r) {

  // TODO only required for Jackson deserialization, can we avoid?
  def this() = this(null, null)

  def evaluate(left: Value, right: Value): Value =
    Value(left.asDouble + right.asDouble)

}

/** A basic subtraction expression.
  *
  * @param l the left operand
  * @param r the right operand
  */
class Subtraction(l: Expression, r: Expression) extends BasicMath(l, r) {

  // TODO only required for Jackson deserialization, can we avoid?
  def this() = this(null, null)

  override def evaluate(left: Value, right: Value): Value =
    Value(left.asDouble - right.asDouble)

}

/** A basic multiplication expression.
  *
  * @param l the left operand
  * @param r the right operand
  */
class Multiplication(l: Expression, r: Expression) extends BasicMath(l, r) {

  // TODO only required for Jackson deserialization, can we avoid?
  def this() = this(null, null)

  override def evaluate(left: Value, right: Value): Value =
    Value(left.asDouble * right.asDouble)

}

/** A basic division expression.
  *
  * @param l the left operand
  * @param r the right operand
  */
class Division(l: Expression, r: Expression) extends BasicMath(l, r) {

  // TODO only required for Jackson deserialization, can we avoid?
  def this() = this(null, null)

  override def evaluate(left: Value, right: Value): Value =
    Value(left.asDouble / right.asDouble)

}