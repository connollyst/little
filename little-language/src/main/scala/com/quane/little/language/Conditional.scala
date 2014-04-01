package com.quane.little.language

import com.google.common.base.Objects
import com.quane.little.language.data.{Value, Nada}


/** A conditional is an [[com.quane.little.language.Expression]] which evaluates
  * a [[com.quane.little.language.Block]] if its test evaluates to `true`.
  *
  * @author Sean Connolly
  */
class Conditional(val test: Expression, val block: Block)
  extends Expression {

  def this(scope: Scope, test: Expression) = {
    this(test, new Block(scope))
  }

  def steps: List[Expression] = block.steps

  def addStep(step: Expression): Conditional = {
    block.addStep(step)
    this
  }

  /** Evaluate the conditional statement; only if the ''test'' evaluates
    * to `true`, the ''function'' is evaluated.
    */
  override def evaluate: Value = {
    if (test.evaluate.asBool) {
      block.evaluate
    } else {
      // TODO we should have an ELSE block
      new Nada
    }
  }

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("test", test)
      .add("block", block)
      .toString

}