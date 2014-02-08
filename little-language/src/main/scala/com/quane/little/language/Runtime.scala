package com.quane.little.language

import scala.collection.mutable.Map

class Runtime
  extends Scope {

  val runtime: Runtime = this
  var scope: Scope = this

  private val functions = Map[String, Function]()

  def isFunctionDefined(name: String): Boolean = {
    functions.contains(name)
  }

  def saveFunction(name: String, function: Function) {
    log.info("Saving function '" + name + "'")
    functions(name) = function
  }

  /** Returns the [[com.quane.little.language.Function]] by the given name if it
    * is defined.
    *
    * @param name the name of the function
    * @return the function if defined
    */
  def fetchFunction(name: String): Function = {
    if (functions.contains(name)) {
      functions(name)
    } else {
      // TODO what to do?
      throw new RuntimeException("No function '" + name + "' in scope.")
    }
  }

}
