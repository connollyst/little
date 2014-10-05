package com.quane.little.language

import com.google.common.base.Objects
import com.quane.little.language.data.ValueType
import com.quane.little.language.data.ValueType._

/** Code is the root of the little language.
  *
  * @author Sean Connolly
  */
trait Code {

  override def toString: String = Objects.toStringHelper(getClass).toString

}

trait EvaluableCode extends Code {
  // TODO is there any non-evaluable code??

  /** Returns this expression's [[com.quane.little.language.data.ValueType]]
    *
    * @return the return value type
    */
  // TODO remove default
  // TODO requires Scope?
  def returnType: ValueType = ValueType.Nada

}