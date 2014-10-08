package com.quane.little.language.math

import com.google.common.base.Objects
import com.quane.little.language.data.Value
import com.quane.little.language.{Code, Scope}

import scala.math._

sealed trait AdvancedMath extends Math

class ArcTan(val x: Code) extends AdvancedMath {

  override def evaluate(scope: Scope): Value = {
    val numberA = x.evaluate(scope).asInt
    Value(atan(numberA))
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[ArcTan]

  override def equals(other: Any): Boolean = other match {
    case that: ArcTan if that.getClass == getClass =>
      x == that.x
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(x)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("x", x)
      .toString

}

class ArcTan2(val l: Code, val r: Code) extends AdvancedMath {

  override def evaluate(scope: Scope): Value = {
    val left = l.evaluate(scope).asInt
    val right = r.evaluate(scope).asInt
    Value(atan2(left, right))
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[ArcTan2]

  override def equals(other: Any): Boolean = other match {
    case that: ArcTan2 if that.getClass == getClass =>
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