package com.quane.little.language

import com.quane.little.language.data.Nada
import com.google.common.base.Objects


/** A conditional is an [[com.quane.little.language.Expression]] which evaluates
  * a [[com.quane.little.language.Block]] if its test evaluates to {@code true}.
  *
  * @author Sean Connolly
  */
class Conditional(val test: Expression, val block: Block)
  extends Expression {

  def steps: List[Expression] = block.steps

  /** Evaluate the conditional statement; only if the <i>test</i> evaluates
    * to {@code true}, the <i>function</i> is evaluated.
    */
  override def evaluate: Nada = {
    if (test.evaluate.asBool) {
      block.evaluate
    }
    new Nada
  }

  override def toString: String = {
    Objects.toStringHelper(getClass)
      .add("test", test)
      .add("block", block)
      .toString
  }

}