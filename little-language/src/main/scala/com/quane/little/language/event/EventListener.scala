package com.quane.little.language.event

import com.quane.little.language.{FunctionReference, Expression}
import com.quane.little.language.data.Nada
import com.google.common.base.Objects

class EventListener(val event: LittleEvent, val function: FunctionReference)
  extends Expression {

  def evaluate: Nada = {
    function.evaluate
    new Nada
  }

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("event", event)
      .add("function", function)
      .toString

}