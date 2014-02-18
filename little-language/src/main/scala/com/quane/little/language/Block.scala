package com.quane.little.language

import com.quane.little.language.data.Value
import scala.collection.mutable.ListBuffer


/** A block is a scope consisting of one or more expressions.
  *
  * @author Sean Connolly
  */
class Block(var scope: Scope, _steps: ListBuffer[Expression])
  extends Expression
  with Scope {

  def this(scope: Scope) = this(scope, ListBuffer[Expression]())

  def length: Int = _steps.length

  def steps: List[Expression] = _steps.toList

  def clear(): Unit = _steps.clear()

  def addStep(step: Expression): Block = {
    _steps += step
    this
  }

  def evaluate: Value = {
    _steps.map(step => step.evaluate).last
  }

  override def toString: String = {
    getClass.getSimpleName + "{" + steps + "}"
  }

}