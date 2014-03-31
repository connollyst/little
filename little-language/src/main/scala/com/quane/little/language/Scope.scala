package com.quane.little.language

import com.fasterxml.jackson.annotation.JsonIgnore
import com.quane.little.language.data._
import scala.collection.mutable

/** A Scope defines a space in which
  * [[com.quane.little.language.data.Variable]] objects can be stored and
  * retrieved.
  *
  * Scopes can be embedded within each other in a hierarchical fashion. As
  * such, reading a [[com.quane.little.language.data.Variable]] from a low
  * level scope may actually return a value from a higher level scope.
  * Similarly, a [[com.quane.little.language.data.Variable]] defined at a low
  * level scope, may block visibility of a previously defined
  * [[com.quane.little.language.data.Variable]] by the same name in a higher
  * level scope.
  *
  * @author Sean Connolly
  */
trait Scope {

  private val variables = mutable.Map[String, Variable]()

  @JsonIgnore
  var scope: Scope

  /** Returns the current runtime.
    *
    * @return the current runtime.
    */
  // TODO should we make sure that the parent scope is different?
  def runtime: Runtime = scope.runtime

  /** Stores the given [[com.quane.little.language.data.Variable]].
    *
    * If a [[com.quane.little.language.data.Variable]] of the same name
    * exists in this, or a higher scope, it is updated. Otherwise it is
    * created in this scope.
    *
    * @param name the variable name to be stored
    * @param value the variable value to be stored
    */
  def save(name: String, value: Value) {
    save(new Variable(name, value))
  }

  /** Stores the given [[com.quane.little.language.data.Variable]].
    *
    * If a [[com.quane.little.language.data.Variable]] of the same name
    * exists in this, or a higher scope, it is updated. Otherwise it is
    * created in this scope.
    *
    * @param variable the variable to be stored
    */
  def save(variable: Variable) {
    val name = variable.name
    if (scope != null && scope.isDefined(name)) {
      scope.save(variable)
    } else {
      variables(name) = variable
    }
  }

  /** Returns the [[com.quane.little.language.data.Variable]] by the given
    * name if it is defined in this, ''or any greater''
    * [[com.quane.little.language.Scope]]. If not found,
    * [[com.quane.little.language.data.Nada]] is returned.
    *
    * @param name the name of the variable
    * @return the variable if it is defined,
    *         [[com.quane.little.language.data.Nada]] otherwise
    */
  def fetch(name: String): Variable = {
    if (variables.contains(name)) {
      variables(name)
    } else if (scope != null && scope != this) {
      scope.fetch(name)
    } else {
      new Variable(name, new Nada)
    }
  }

  /** Returns true/false if a [[com.quane.little.language.data.Variable]] by the
    * given name is defined in this, ''or any greater''
    * [[com.quane.little.language.Scope]].
    *
    * @param name the name of the variable
    * @return is a variable for the given name defined?
    */
  def isDefined(name: String): Boolean = {
    fetch(name).value.valueType != ValueType.Nada
  }

}