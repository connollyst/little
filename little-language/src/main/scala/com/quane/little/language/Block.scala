package com.quane.little.language

import com.quane.little.language.data.Value
import scala.collection.mutable.ListBuffer


/** A block is a scope consisting of one or more expressions.
  *
  * @author Sean Connolly
  */
class Block(var scope: Scope, steps: ListBuffer[Expression])
  extends Expression
  with Scope {

  def this(scope: Scope) = this(scope, ListBuffer[Expression]())

  def length: Int = steps.length

  def addStep(step: Expression): Block = {
    steps += step
    this
  }

  def evaluate: Value = {
    steps.map(step => step.evaluate).last
  }

}