package com.quane.little.language

import org.eintr.loglady.Logging
import com.quane.little.language.memory.Pointer
import com.quane.little.language.data.Value
import com.google.common.base.Objects

abstract class Statement
  extends Expression

class SetStatement(pointer: Pointer, value: Expression)
  extends Statement
  with Logging {

  def this(pointer: Pointer, value: String) = {
    this(pointer, new Value(value))
  }

  def this(pointer: Pointer, value: Boolean) = {
    this(pointer, new Value(value))
  }

  def this(pointer: Pointer, value: Int) = {
    this(pointer, new Value(value))
  }

  def this(pointer: Pointer, value: Double) = {
    this(pointer, new Value(value))
  }

  def this(scope: Scope, name: String, value: Expression) = {
    this(new Pointer(scope, name), value)
  }

  def this(scope: Scope, name: String, value: String) = {
    this(new Pointer(scope, name), value)
  }

  def this(scope: Scope, name: String, value: Boolean) = {
    this(new Pointer(scope, name), value)
  }

  def this(scope: Scope, name: String, value: Int) = {
    this(new Pointer(scope, name), value)
  }

  def this(scope: Scope, name: String, value: Double) = {
    this(new Pointer(scope, name), value)
  }

  def name: String = pointer.variableName

  // TODO we shouldn't need this
  def valueString: String = value.toString

  def evaluate: Value = {
    val name = pointer.variableName
    val actualValue = value.evaluate
    pointer.scope.save(name, actualValue)
    actualValue
  }

  override def toString: String = {
    Objects.toStringHelper(getClass)
      .add("name", pointer.variableName)
      .add("value", value)
      .toString
  }

}

class GetStatement(pointer: Pointer)
  extends Statement
  with Logging {

  def this(scope: Scope, name: String) = {
    this(new Pointer(scope, name))
  }

  def name: String = pointer.variableName

  def evaluate: Value = {
    val name = pointer.variableName
    val variable = pointer.scope.fetch(name)
    variable.value
  }

  override def toString: String = {
    Objects.toStringHelper(getClass)
      .add("name", pointer.variableName)
      .toString
  }

}

class PrintStatement(value: Expression)
  extends Statement
  with Logging {

  def this(value: String) = {
    this(new Value(value))
  }

  // TODO we shouldn't need this
  def valueString: String = value.toString

  def evaluate: Value = {
    val text = value.evaluate
    // TODO this should display a speech bubble over the guy
    log.error(text.asText)
    text
  }

  override def toString: String = {
    Objects.toStringHelper(getClass)
      .add("value", value)
      .toString
  }

}
