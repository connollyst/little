package com.quane.little.language

import com.quane.little.language.data.Value
import com.google.common.base.Objects


/** An expression is one of the lowest level components of the language. It has a
  * return type and can be evaluated.
  *
  * @author Sean Connolly
  */
trait Expression {

  // [T <: Value]
  def evaluate: Value

  override def toString: String =
    Objects.toStringHelper(getClass).toString

}