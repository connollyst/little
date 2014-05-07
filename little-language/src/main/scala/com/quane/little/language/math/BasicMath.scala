package com.quane.little.language.math

import com.quane.little.language.{Scope, Expression}
import com.quane.little.language.data.Value
import com.google.common.base.Objects

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

class Addition(l: Expression, r: Expression) extends BasicMath(l, r) {

  // TODO only required for Jackson deserialization, can we avoid?
  def this() = this(null, null)

  def evaluate(left: Value, right: Value): Value =
    Value(left.asDouble + right.asDouble)

}

class Subtraction(l: Expression, r: Expression) extends BasicMath(l, r) {

  // TODO only required for Jackson deserialization, can we avoid?
  def this() = this(null, null)

  override def evaluate(left: Value, right: Value): Value =
    Value(left.asDouble - right.asDouble)

}

class Multiplication(l: Expression, r: Expression) extends BasicMath(l, r) {

  // TODO only required for Jackson deserialization, can we avoid?
  def this() = this(null, null)

  override def evaluate(left: Value, right: Value): Value =
    Value(left.asDouble * right.asDouble)

}

class Division(l: Expression, r: Expression) extends BasicMath(l, r) {

  // TODO only required for Jackson deserialization, can we avoid?
  def this() = this(null, null)

  override def evaluate(left: Value, right: Value): Value =
    Value(left.asDouble / right.asDouble)

}