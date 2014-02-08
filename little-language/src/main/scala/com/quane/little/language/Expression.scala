package com.quane.little.language

import com.quane.little.language.data.Value


/** An expression is one of the lowest level components of the language. It has a
  * return type and can be evaluated.
  *
  * @author Sean Connolly
  */
trait Expression {

  // [T <: Value]
  def evaluate: Value

}