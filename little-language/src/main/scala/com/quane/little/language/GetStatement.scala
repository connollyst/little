package com.quane.little.language

import com.quane.little.language.data.Value
import com.google.common.base.Objects

/**
 *
 *
 * @author Sean Connolly
 */
class GetStatement(val name: String)
  extends Expression {

  override def evaluate(scope: Scope): Value = {
    val variable = new Pointer(name).resolve(scope)
    variable.value
  }

  override def equals(that: Any) = that match {
    case g: GetStatement => name.equals(g.name)
    case _ => false
  }

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("name", name)
      .toString

}

