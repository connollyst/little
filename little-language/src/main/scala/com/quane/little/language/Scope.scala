package com.quane.little.language

import scala.collection.mutable.Map
import com.quane.little.language.data.{NoValueType, Value, Variable, Nada}
import org.eintr.loglady.Logging

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
trait Scope
  extends Logging {

  var scope: Scope

  private val variables = Map[String, Variable]()
  private val functions = Map[String, Function]()

  /** Stores the given [[com.quane.little.language.data.Variable]] in memory.
    *
    * If a [[com.quane.little.language.data.Variable]] of the same name
    * exists in this, or a higher scope, it is updated. Otherwise it is
    * created in this scope.
    *
    * @param variable the variable to be stored in memory
    */
  def save(variable: Variable) {
    val name = variable.name
    val value = variable.value
    if (scope != null && scope.isDefined(name)) {
      scope.save(variable)
    } else {
      log.info("Saving '" + name + "' as '" + value.primitive + "'")
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
    } else if (scope != null) {
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
    fetch(name).value.valueType != NoValueType
  }

  def isFunctionDefined(name: String): Boolean = {
    fetchFunction(name) != null
  }

  def saveFunction(name: String, function: Function) {
    if (scope != null && scope.isDefined(name)) {
      scope.saveFunction(name, function)
    } else {
      log.info("Saving function '" + name + "'")
      functions(name) = function
    }
  }

  /** Returns the [[com.quane.little.language.Function]] by the given name if it
    * is defined in this, ''or any greater'' [[com.quane.little.language.Scope]].
    *
    * @param name the name of the function
    * @return the function if defined
    */
  def fetchFunction(name: String): Function = {
    if (functions.contains(name)) {
      functions(name)
    } else if (scope != null) {
      scope.fetchFunction(name)
    } else {
      // TODO what to do?
      throw new RuntimeException("No function '" + name + "' in scope.")
    }
  }

}