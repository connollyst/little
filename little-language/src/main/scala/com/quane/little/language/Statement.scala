package com.quane.little.language

import com.google.common.base.Objects
import com.quane.little.Logging
import com.quane.little.language.data.Value

abstract class Statement
  extends Expression

class SetStatement(pointer: Pointer, val value: Expression)
  extends Statement {

  def this(pointer: Pointer, value: String) =
    this(pointer, Value(value))

  def this(pointer: Pointer, value: Boolean) =
    this(pointer, Value(value))

  def this(pointer: Pointer, value: Int) =
    this(pointer, Value(value))

  def this(pointer: Pointer, value: Double) =
    this(pointer, Value(value))

  def this(name: String, value: Expression) =
    this(new Pointer(name), value)

  def this(name: String, value: String) =
    this(new Pointer(name), value)

  def this(name: String, value: Boolean) =
    this(new Pointer(name), value)

  def this(name: String, value: Int) =
    this(new Pointer(name), value)

  def this(name: String, value: Double) =
    this(new Pointer(name), value)

  def name: String = pointer.variableName

  override def evaluate(scope: Scope): Value = {
    val actualValue = value.evaluate(scope)
    pointer.update(scope, actualValue)
    actualValue
  }

  override def equals(that: Any) = that match {
    case s: SetStatement => name.equals(s.name) && value.equals(s.value)
    case _ => false
  }

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("name", pointer.variableName)
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
