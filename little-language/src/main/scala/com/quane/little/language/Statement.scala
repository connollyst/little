package com.quane.little.language

/** A statement is code which is evaluated for it's side effects only;
  * statements have no return value.
  *
  * @author Sean Connolly
  */
trait Statement extends EvaluableCode {

  /** Evaluate the statement.
    *
    * @param scope the scope in which to evaluate
    */
  def evaluate(scope: Scope): Unit

}