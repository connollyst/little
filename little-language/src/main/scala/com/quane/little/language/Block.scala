package com.quane.little.language

import com.google.common.base.Objects
import com.quane.little.language.data.{Nada, Value}
import scala.collection.mutable.ListBuffer


/** An expression consisting of zero or more expressions, to be evaluated in
  * sequence, within a new [[Scope]].
  *
  * @author Sean Connolly
  */
class Block
  extends Expression {

  private val _steps: ListBuffer[Expression] = ListBuffer[Expression]()

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

  override def evaluate(scope: Scope): Value = {
    val blockScope = new Scope(scope)
    val stepOutput = _steps.map(_.evaluate(blockScope))
    if (stepOutput.isEmpty) {
      new Nada
    } else {
      stepOutput.last
    }
  }

  override def equals(other: Any): Boolean =
    other match {
      case that: Block => steps.equals(that.steps)
      case _ => false
    }

  override def hashCode(): Int = 42 * steps.hashCode() + 137

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("steps", steps)
      .toString

}