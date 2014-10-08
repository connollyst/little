package com.quane.little.language

import com.quane.little.language.data.ValueType
import com.quane.little.language.data.ValueType.ValueType

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

  /** Returns this statement's [[ValueType]]
    *
    * @return the return value type
    */
  override def returnType: ValueType = ValueType.Nothing

}