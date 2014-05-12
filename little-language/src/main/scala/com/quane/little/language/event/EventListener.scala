package com.quane.little.language.event

import com.google.common.base.Objects
import com.quane.little.language._
import com.quane.little.language.event.Event.Event

class EventListener(val event: Event)
  extends Code {

  private val block = new Block

  def stepCount: Int = block.length

  def addStep(step: EvaluableCode): EventListener = {
    block.addStep(step)
    this
  }

  def steps: List[EvaluableCode] = block.steps

  def steps_=(steps: List[EvaluableCode]) = block.steps = steps

  def evaluate(scope: Scope): Unit = {
    val listenerScope = new Scope(scope)
    block.evaluate(listenerScope)
  }

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("event", event)
      .add("block", block)
      .toString

}