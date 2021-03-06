package com.quane.little.language

import com.google.common.base.Objects
import com.quane.little.language.data.ValueType.ValueType
import com.quane.little.language.data.{Value, ValueType}

/** An expression to retrieve a variable's value from memory.
  *
  * @author Sean Connolly
  */
class Getter(val name: String) extends Code {

  // TODO depends on Variable
  override def returnType: ValueType = ValueType.Something

  override def evaluate(scope: Scope): Value = {
    val variable = new Pointer(name).resolve(scope)
    variable.value
  }

  override def equals(that: Any) = that match {
    case g: Getter => name.equals(g.name)
    case _ => false
  }

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("name", name)
      .toString

}

