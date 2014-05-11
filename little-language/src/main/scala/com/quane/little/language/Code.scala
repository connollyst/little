package com.quane.little.language

import com.google.common.base.Objects

/** Code is the root of the little language.
  *
  * @author Sean Connolly
  */
trait Code {

  override def toString: String = Objects.toStringHelper(getClass).toString

}

trait EvaluableCode extends Code