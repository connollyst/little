package com.quane.little.language

import com.google.common.base.Objects
import com.quane.little.language.data.ValueType.ValueType
import com.quane.little.language.data.{Value, ValueType}


/** [[com.quane.little.language.Code]] which evaluates the `then` block if the `test` evaluates to `true`, and the
  * `otherwise` block if not.
  *
  * @author Sean Connolly
  */
class Conditional(val test: Code, val then: Block, val otherwise: Block) extends Code {

  def this(test: Code) = this(test, new Block, new Block)

  def addStep(step: Code): Conditional = {
    then.addStep(step)
    this
  }

  // TODO only returns something if block returns something
  override def returnType: ValueType = ValueType.Something

  /** Evaluate the conditional statement.
    *
    * The `then` block is evaluated if the `test` evaluates to `true`, otherwise the `otherwise` block is evaluated.
    */
  override def evaluate(scope: Scope): Value =
  // TODO asBool is not very safe
    if (test.evaluate(scope).asBool.primitive) {
      then.evaluate(scope)
    } else {
      otherwise.evaluate(scope)
    }

  def canEqual(other: Any): Boolean = other.isInstanceOf[Conditional]

  override def equals(other: Any): Boolean = other match {
    case that: Conditional =>
      (that canEqual this) &&
        test == that.test &&
        then == that.then &&
        otherwise == that.otherwise
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(test, then, otherwise)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("test", test)
      .add("then", then)
      .add("else", otherwise)
      .toString

}