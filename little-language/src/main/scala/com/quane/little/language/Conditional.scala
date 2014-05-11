package com.quane.little.language

import com.google.common.base.Objects
import com.quane.little.language.data.Value


/** An [[Expression]] which evaluates the `then` block if the `test` evaluates
  * to `true`, and the `otherwise` block if not.
  *
  * @author Sean Connolly
  */
class Conditional(val test: Expression, val then: Block, val otherwise: Block)
  extends Expression {

  def this(test: Expression) = {
    this(test, new Block, new Block)
  }

  def steps: List[Expression] = then.steps

  def addStep(step: Expression): Conditional = {
    then.addStep(step)
    this
  }

  /** Evaluate the conditional statement; only if the ''test'' evaluates
    * to `true`, the ''function'' is evaluated.
    */
  override def evaluate(scope: Scope): Value =
    if (test.evaluate(scope).asBool) {
      then.evaluate(scope)
    } else {
      otherwise.evaluate(scope)
    }

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("test", test)
      .add("then", then)
      .add("else", otherwise)
      .toString

}