package com.quane.little.language

import scala.collection.mutable.Map
import com.quane.little.language.data.Variable
import org.eintr.loglady.Logging
import com.quane.little.language.data.Nada

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

  private val memory = Map[String, Variable]()

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
      memory(name) = variable
    }
  }

  /** Returns the [[com.quane.little.language.data.Variable]] by the given
    * name if it is defined in this, ''or any greater''
    * [[com.quane.little.language.Scope]]. If not found,
    * [[com.quane.little.language.data.Nada]] is returned.
    *
    * @param name the name of the variable to fetch
    * @return the variable if it is defined, None otherwise
    */
  def fetch(name: String): Variable = {
    if (memory.contains(name)) {
      memory(name)
    } else if (scope != null) {
      scope.fetch(name)
    } else {
      new Variable(name, new Nada)
    }
  }

  /** Returns true/false if a [[com.quane.little.language.data.Variable]] by the given name is defined in
    * this, ''or any greater'' [[com.quane.little.language.Scope]].
    *
    * @param name is a variable for the given name defined?
    */
  def isDefined(name: String): Boolean = {
    fetch(name) != null
  }

}