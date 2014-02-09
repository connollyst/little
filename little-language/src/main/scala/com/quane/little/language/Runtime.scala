package com.quane.little.language

import scala.collection.mutable.Map

class Runtime
  extends Scope {

  val runtime: Runtime = this
  var scope: Scope = this

  private val functions = Map[String, FunctionDefinition]()

  def isFunctionDefined(name: String): Boolean = {
    functions.contains(name)
  }

  def saveFunction(function: FunctionDefinition) {
    log.info("Saving function '" + function.name + "'")
    functions(function.name) = function
  }

  /** Returns the named [[com.quane.little.language.FunctionDefinition]].
    *
    * @param name the name of the function
    * @return the function if defined
    */
  def fetchFunction(name: String): FunctionDefinition = {
    if (functions.contains(name)) {
      functions(name)
    } else {
      // TODO what to do?
      throw new RuntimeException("No function '" + name + "' in scope.")
    }
  }

}
