package com.quane.little.language

import com.google.common.base.Objects
import com.quane.little.language.data.Value
import com.quane.little.tools.Logging

sealed trait Statement extends EvaluableCode {

  def evaluate(scope: Scope): Unit

}

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

class GetStatement(val name: String)
  extends Expression {

  override def evaluate(scope: Scope): Value = {
    val variable = new Pointer(name).resolve(scope)
    variable.value
  }

  override def equals(that: Any) = that match {
    case g: GetStatement => name.equals(g.name)
    case _ => false
  }

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("name", name)
      .toString

}

class PrintStatement(val value: Expression)
  extends Statement
  with Logging {

  override def evaluate(scope: Scope): Unit = {
    val text = value.evaluate(scope)
    // TODO this should display a speech bubble over the guy
    error(text.asText)
  }

  override def equals(that: Any) = that match {
    case p: PrintStatement => value.equals(p.value)
    case _ => false
  }

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("value", value)
      .toString

}
