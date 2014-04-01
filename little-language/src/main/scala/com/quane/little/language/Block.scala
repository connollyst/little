package com.quane.little.language

import com.fasterxml.jackson.annotation.JsonCreator
import com.google.common.base.Objects
import com.quane.little.language.data.Value
import scala.collection.mutable.ListBuffer


/** A block is a scope consisting of one or more expressions.
  *
  * @author Sean Connolly
  */
class Block(parent: Option[Scope] = None, _steps: ListBuffer[Expression] = ListBuffer[Expression]())
  extends Scope(parent) with Expression {

  def this(scope: Scope) = this(Some(scope))

  // TODO this constructor shouldn't be necessary
  @JsonCreator
  def this() = this(None, ListBuffer[Expression]())

  def length: Int = _steps.length

  def steps: List[Expression] = _steps.toList

  def steps_=(steps: List[Expression]): Unit = {
    _steps.clear()
    _steps ++= steps
  }

  def clear(): Unit = _steps.clear()

  def addStep(step: Expression): Block = this += step

  def +=(step: Expression): Block = {
    _steps += step
    this
  }

  def evaluate: Value = _steps.map(step => step.evaluate).last

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("steps", steps)
      .toString

}