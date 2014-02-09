package com.quane.little.language.event

import com.quane.little.language.{Block, Expression}
import com.quane.little.language.data.Nada

class EventListener(val event: LittleEvent, val function: Block)
  extends Expression {

  def evaluate: Nada = {
    function.evaluate
    new Nada
  }

}