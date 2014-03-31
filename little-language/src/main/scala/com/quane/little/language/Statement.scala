package com.quane.little.language

import com.google.common.base.Objects
import com.quane.little.Logging
import com.quane.little.language.data.Value
import com.quane.little.language.memory.Pointer

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

  def this(scope: Scope, name: String, value: Expression) =
    this(new Pointer(scope, name), value)

  def this(scope: Scope, name: String, value: String) =
    this(new Pointer(scope, name), value)

  def this(scope: Scope, name: String, value: Boolean) =
    this(new Pointer(scope, name), value)

  def this(scope: Scope, name: String, value: Int) =
    this(new Pointer(scope, name), value)

  def this(scope: Scope, name: String, value: Double) =
    this(new Pointer(scope, name), value)

  def name: String = pointer.variableName

  def evaluate: Value = {
    val name = pointer.variableName
    val actualValue = value.evaluate
    pointer.scope.save(name, actualValue)
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

  def this(scope: Scope, name: String) = this(new Pointer(scope, name))

  def name: String = pointer.variableName

  def evaluate: Value = {
    val name = pointer.variableName
    val variable = pointer.scope.fetch(name)
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

  def evaluate: Value = {
    val text = value.evaluate
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
