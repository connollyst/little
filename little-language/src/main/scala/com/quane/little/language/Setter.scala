package com.quane.little.language

import com.quane.little.language.data.Value
import com.google.common.base.Objects

/** Factory for [[Setter]] instances. **/
object Setter {

  def apply(name: String, value: String): Setter = new Setter(name, Value(value))

  def apply(name: String, value: Boolean): Setter = new Setter(name, Value(value))

  def apply(name: String, value: Int): Setter = new Setter(name, Value(value))

  def apply(name: String, value: Double): Setter = new Setter(name, Value(value))

}

/** Assign a `value` to a variable, identified by it's `name`.
  *
  * @param name the name of the variable to assign
  * @param value the value to assign to the variable
  * @author Sean Connolly
  */
class Setter(val name: String, val value: Expression)
  extends Statement {

  /** Evaluate the assignment statement.
    *
    * @param scope the scope in which to evaluate
    */
  override def evaluate(scope: Scope): Unit =
    new Pointer(name).update(scope, value.evaluate(scope))

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