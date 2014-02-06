package com.quane.little.language.event

import com.quane.little.language.Expression
import com.quane.little.language.Function
import com.quane.little.language.data.Nada

class EventListener(val event: LittleEvent, val function: Function)
  extends Expression[Nada] {

  def evaluate: Nada = {
    function.evaluate
    new Nada
  }

}