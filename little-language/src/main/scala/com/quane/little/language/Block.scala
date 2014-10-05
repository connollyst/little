package com.quane.little.language

import com.google.common.base.Objects
import com.quane.little.language.data.ValueType.ValueType
import com.quane.little.language.data.{Nada, Value, ValueType}

import scala.collection.mutable.ListBuffer


/** An expression consisting of zero or more expressions, to be evaluated in
  * sequence, within a new [[Scope]].
  *
  * @author Sean Connolly
  */
class Block extends Expression {

  private val _steps: ListBuffer[EvaluableCode] = ListBuffer[EvaluableCode]()

  def length: Int = _steps.length

  def empty: Boolean = _steps.isEmpty

  def steps: List[EvaluableCode] = _steps.toList

  def steps_=(steps: List[EvaluableCode]): Unit = {
    _steps.clear()
    _steps ++= steps
  }

  def clear(): Unit = _steps.clear()

  def addStep(step: EvaluableCode): Block = this += step

  def +=(step: EvaluableCode): Block = {
    _steps += step
    this
  }

  /** Returns this block's return value type. If the block is empty, its return
    * value type is [[ValueType.Nada]]. If the block is not empty, its return
    * value type is defined by the last step in the block.
    *
    * @return the return value type of the block
    */
  override def returnType: ValueType =
    if (!empty) {
      steps.last.returnType
    } else {
      ValueType.Nada
    }

  /** Evaluates the block in the provided [[Scope]]. A new scope is defined for
    * the block itself, and then each step is evaluated in order.
    *
    * @param scope the scope in which to evaluate the expression
    * @return the return value of the block
    */
  override def evaluate(scope: Scope): Value = {
    val blockScope = new Scope(scope)
    val stepOutput = _steps.map {
      case e: Expression =>
        e.evaluate(blockScope)
      case s: Statement =>
        s.evaluate(blockScope)
        new Nada
    }
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