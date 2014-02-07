package com.quane.little.language.data

import com.google.common.base.Objects

/** A variable: the mutable association between a name and a value.
  *
  * @author Sean Connolly
  */
class Variable(val name: String, val value: Value) {

  override def toString =
    Objects.toStringHelper(getClass)
      .add("name", name)
      .add("value", value)
      .toString

}
