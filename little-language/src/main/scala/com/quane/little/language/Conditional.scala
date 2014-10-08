package com.quane.little.language

import com.google.common.base.Objects
import com.quane.little.language.data.ValueType.ValueType
import com.quane.little.language.data.{ValueType, Value}


/** An [[Expression]] which evaluates the `then` block if the `test` evaluates
  * to `true`, and the `otherwise` block if not.
  *
  * @author Sean Connolly
  */
class Conditional(val test: Expression, val then: Block, val otherwise: Block)
  extends Expression {

  def this(test: Expression) = this(test, new Block, new Block)

  def addStep(step: EvaluableCode): Conditional = {
    then.addStep(step)
    this
  }

  // TODO only returns something if block returns something
  override def returnType: ValueType = ValueType.Something

  /** Evaluate the conditional statement; only if the ''test'' evaluates
    * to `true`, the ''function'' is evaluated.
    */
  override def evaluate(scope: Scope): Value =
    if (test.evaluate(scope).asBool) {
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