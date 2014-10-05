package com.quane.little.language

import com.quane.little.language.data.Value

/** An expression is one of the lowest level components of the language. It has a
  * return type and can be evaluated.
  *
  * @author Sean Connolly
  */
trait Expression extends EvaluableCode {

  /** Evaluate this expression, in the given [[com.quane.little.language.Scope]],
    * and output it's return [[com.quane.little.language.data.Value]].
    *
    * @param scope the scope in which to evaluate the expression
    * @return the return value of the expression
    */
  def evaluate(scope: Scope): Value

}