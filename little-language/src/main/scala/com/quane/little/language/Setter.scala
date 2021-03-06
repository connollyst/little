package com.quane.little.language

import com.google.common.base.Objects
import com.quane.little.language.data.ValueType.ValueType
import com.quane.little.language.data.{Nada, Value, ValueType}

/** Factory for [[com.quane.little.language.Setter]] instances. */
object Setter {

  def apply(name: String, value: String): Setter = new Setter(name, Value(value))

  def apply(name: String, value: Boolean): Setter = new Setter(name, Value(value))

  def apply(name: String, value: BigDecimal): Setter = new Setter(name, Value(value))

  def apply(name: String, value: Int): Setter = new Setter(name, Value(value))

  def apply(name: String, value: Long): Setter = new Setter(name, Value(value))

  def apply(name: String, value: Float): Setter = new Setter(name, Value(value))

  def apply(name: String, value: Double): Setter = new Setter(name, Value(value))


}

/** Assign a `value` to a variable, identified by it's `name`.
  *
  * @param name the name of the variable to assign
  * @param value the value to assign to the variable
  * @author Sean Connolly
  */
class Setter(val name: String, val value: Code) extends Code {

  /** Returns this statement's [[ValueType]]
    *
    * @return the return value type
    */
  override def returnType: ValueType = ValueType.Nothing

  /** Evaluate the assignment statement.
    *
    * @param scope the scope in which to evaluate
    */
  override def evaluate(scope: Scope): Value = {
    new Pointer(name).update(scope, value.evaluate(scope))
    new Nada
  }

  override def equals(that: Any) = that match {
    case s: Setter => name.equals(s.name) && value.equals(s.value)
    case _ => false
  }

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("name", name)
      .add("value", value)
      .toString

}