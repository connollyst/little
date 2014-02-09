package com.quane.little.language

import org.eintr.loglady.Logging
import com.quane.little.language.memory.Pointer
import com.quane.little.language.data.{Value, Variable}

abstract class Statement
  extends Expression

class Set(pointer: Pointer, value: Expression)
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

  def evaluate: Value = {
    val name = pointer.variableName
    val actualValue = value.evaluate
    pointer.scope.save(name, actualValue)
    actualValue
  }

}

class Get(pointer: Pointer)
  extends Statement
  with Logging {

  def this(scope: Scope, name: String) = {
    this(new Pointer(scope, name))
  }

  def evaluate: Value = {
    val name = pointer.variableName
    val variable = pointer.scope.fetch(name)
    variable.value
  }

}

class Print(value: Expression)
  extends Statement
  with Logging {

  def evaluate: Value = {
    val text = value.evaluate
    // TODO this should display a speech bubble over the guy
    log.error(text.asText)
    text
  }

}
