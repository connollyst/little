package com.quane.little.language

import com.google.common.base.Objects
import com.quane.little.language.data.Value

/** An expression is one of the lowest level components of the language. It has a
  * return type and can be evaluated.
  *
  * @author Sean Connolly
  */
trait Expression {

  def evaluate: Value

  override def toString: String =
    Objects.toStringHelper(getClass).toString

}