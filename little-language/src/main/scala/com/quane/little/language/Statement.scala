package com.quane.little.language

import org.eintr.loglady.Logging
import com.quane.little.language.memory.Pointer
import com.quane.little.language.data.{Value, Variable}

abstract class Statement
  extends Expression[Value]

class SetStatement(pointer: Pointer, value: Expression[Value])
  extends Statement
  with Logging {

  def evaluate: Value = {
    val name = pointer.variableName
    val actualValue = value.evaluate
    log.info("Setting '" + name + "' to " + actualValue.primitive)
    pointer.scope.save(new Variable(name, actualValue))
    actualValue
  }

}

class GetStatement(pointer: Pointer)
  extends Statement
  with Logging {

  def evaluate: Value = {
    val name = pointer.variableName
    log.info("Getting the value of '" + name + "'...")
    val scope = pointer.scope
    val variable = scope.fetch(name)
    variable.value
  }

}

class PrintStatement(value: Expression[Value])
  extends Statement
  with Logging {

  def evaluate: Value = {
    val text = value.evaluate
    // TODO this should display a speech bubble over the guy
    log.error(text.asText)
    text
  }

}

class ReturnStatement(name: String, scope: Scope)
  extends Statement {

  def evaluate: Value = scope.fetch(name).value

}