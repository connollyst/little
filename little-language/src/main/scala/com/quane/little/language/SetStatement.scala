package com.quane.little.language

import com.quane.little.language.data.Value
import com.google.common.base.Objects

object SetStatement {

  def apply(name: String, value: String): SetStatement = new SetStatement(name, Value(value))

  def apply(name: String, value: Boolean): SetStatement = new SetStatement(name, Value(value))

  def apply(name: String, value: Int): SetStatement = new SetStatement(name, Value(value))

  def apply(name: String, value: Double): SetStatement = new SetStatement(name, Value(value))

}

class SetStatement(val name: String, val value: Expression)
  extends Statement {

  override def evaluate(scope: Scope): Unit =
    new Pointer(name).update(scope, value.evaluate(scope))

  override def equals(that: Any) = that match {
    case s: SetStatement => name.equals(s.name) && value.equals(s.value)
    case _ => false
  }

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("name", name)
      .add("value", value)
      .toString

}