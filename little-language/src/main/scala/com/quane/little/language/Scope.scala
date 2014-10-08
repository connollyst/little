package com.quane.little.language

import com.quane.little.language.data._
import scala.collection.mutable

/** A Scope defines a space in which [[Variable]] objects can be stored and
  * retrieved.
  *
  * Scopes can be embedded within each other in a hierarchical fashion. As such,
  * fetching a [[Variable]] from a low level scope may actually return a value
  * from a higher level scope. Similarly, a [[Variable]] defined at a low level
  * scope, may block visibility of a previously defined [[Variable]] by the same
  * name in a higher level scope.
  *
  * @author Sean Connolly
  */
class Scope(parent: Option[Scope]) {

  def this(parent: Scope) = this(Some(parent))

  private val variables = mutable.Map[String, Variable]()

  /** Returns the current runtime.
    *
    * @return the current runtime.
    */
  // TODO should we make sure that the parent scope is different?
  def runtime: Runtime =
    parent match {
      case Some(p) => p.runtime
      case None => throw new IllegalAccessException(
        "Reached top of stack without finding a Runtime."
      )
    }

  /** Stores the given [[Variable]].
    *
    * If a [[Variable]] of the same name exists in this, or a higher scope, it
    * is updated. Otherwise it is created in this scope.
    *
    * @param name the variable name to be stored
    * @param value the variable value to be stored
    */
  def save(name: String, value: Value): Unit = save(new Variable(name, value))

  /** Stores the given [[Variable]].
    *
    * If a [[Variable]] of the same name exists in this, or a higher scope, it
    * is updated. Otherwise it is created in this scope.
    *
    * @param variable the variable to be stored
    */
  def save(variable: Variable): Unit = {
    val name = variable.name
    parent match {
      case Some(p) =>
        if (p.contains(name)) {
          p.save(variable)
        } else {
          variables += (name -> variable)
        }
      case None => // TODO can we reduce redundancy here?
        variables += (name -> variable)
    }
  }

  /** Returns the [[Variable]] by the give name if it is defined in this,
    * ''or any greater'' [[Scope]]. If not found, [[Nada]] is returned.
    *
    * @param name the name of the variable
    * @return the variable if it is defined, [[Nada]] otherwise
    */
  def fetch(name: String): Variable =
    if (variables.contains(name)) {
      variables(name)
    } else {
      parent match {
        case Some(p) => p.fetch(name)
        case None => new Variable(name, new Nada) // TODO avoid newing
      }
    }

  /** Returns true/false if a [[Variable]] by the given name is defined in this,
    * ''or any greater'' [[Scope]].
    *
    * @param name the name of the variable
    * @return is a variable for the given name defined?
    */
  def contains(name: String): Boolean =
    fetch(name).value.returnType != ValueType.Nothing

}