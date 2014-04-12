package com.quane.little.language

import com.google.common.base.Objects
import com.quane.little.language.data.Value
import com.quane.little.tools.Logging

abstract class Statement
  extends Expression

object SetStatement {

  def apply(pointer: Pointer, value: String): SetStatement = this(pointer, Value(value))

  def apply(pointer: Pointer, value: Boolean): SetStatement = this(pointer, Value(value))

  def apply(pointer: Pointer, value: Int): SetStatement = this(pointer, Value(value))

  def apply(pointer: Pointer, value: Double): SetStatement = this(pointer, Value(value))

  def apply(pointer: Pointer, value: Expression): SetStatement = new SetStatement(pointer.variableName, value)

  def apply(name: String, value: String): SetStatement = new SetStatement(name, Value(value))

  def apply(name: String, value: Boolean): SetStatement = new SetStatement(name, Value(value))

  def apply(name: String, value: Int): SetStatement = new SetStatement(name, Value(value))

  def apply(name: String, value: Double): SetStatement = new SetStatement(name, Value(value))

}

class SetStatement(val name: String, val value: Expression)
  extends Statement {

  override def evaluate(scope: Scope): Value = {
    val actualValue = value.evaluate(scope)
    new Pointer(name).update(scope, actualValue)
    actualValue
  }

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

class GetStatement(pointer: Pointer)
  extends Statement {

  def this(name: String) = this(new Pointer(name))

  def name: String = pointer.variableName

  override def evaluate(scope: Scope): Value = {
    val variable = pointer.resolve(scope)
    variable.value
  }

  override def equals(that: Any) = that match {
    case g: GetStatement => name.equals(g.name)
    case _ => false
  }

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("name", pointer.variableName)
      .toString

}

class PrintStatement(val value: Expression)
  extends Statement
  with Logging {

  def this(value: String) = this(Value(value))

  override def evaluate(scope: Scope): Value = {
    val text = value.evaluate(scope)
    // TODO this should display a speech bubble over the guy
    error(text.asText)
    text
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
