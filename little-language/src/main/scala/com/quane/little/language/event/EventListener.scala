package com.quane.little.language.event

import com.quane.little.language.{FunctionReference, Expression}
import com.quane.little.language.data.Nada

class EventListener(val event: LittleEvent, val function: FunctionReference)
  extends Expression {

  def evaluate: Nada = {
    function.evaluate
    new Nada
  }

}