package com.quane.little.language

import scala.collection.mutable.Map
import com.quane.little.language.data.Variable
import org.eintr.loglady.Logging

/** A Scope defines a space in which {@code Variable} objects can be stored
  * and retrieved.
  *
  * Scopes can be embedded within each other in a hierarchical fashion. As
  * such, reading a {@code Variable} from a low level scope may actually
  * return a value from a higher level scope. Similarly, a {@code Variable}
  * defined at a low level scope, may block visibility of a previously defined
  * {@code Variable} by the same name in a higher level scope.
  *
  * @author Sean Connolly
  */
trait Scope
        extends Logging {

    var scope: Scope

    private val memory = Map[String, Variable]()

    /** Stores the given {@code Variable} in memory.
      *
      * If a {@code Variable} of the same name exists in this, or a higher
      * {@code Scope}, it is updated. Otherwise it is created in this
      * {@code Scope}.
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

    /** Returns the {@code Variable} by the given name if it is defined in
      * this, <i>or any greater</i> {@code Scope}. If not, {@value null} is
      * returned.
      *
      * @param name the name of the variable to fetch
      * @return the variable if it is defined, null otherwise
      */
    def fetch(name: String): Variable = {
        if (memory.contains(name)) {
            memory(name)
        } else if (scope != null) {
            scope.fetch(name)
        } else {
            null // TODO null bad
        }
    }

    /** Returns true/false if a {@code Variable} by the given name is defined
      * in this, <b>or any greater</b> {@code Scope}.
      *
      * @param name is a variable for the given name defined?
      */
    def isDefined(name: String): Boolean = {
        fetch(name) != null;
    }

}