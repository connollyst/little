package com.quane.little.language.event

import com.google.common.base.Objects
import com.quane.little.language.data.Nada
import com.quane.little.language.{Scope, FunctionReference, Expression}
import com.quane.little.language.event.Event.Event

class EventListener(val event: Event, val function: FunctionReference)
  extends Expression {

  override def evaluate(scope: Scope): Nada = {
    function.evaluate(scope)
    new Nada
  }

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("event", event)
      .add("function", function)
      .toString

}