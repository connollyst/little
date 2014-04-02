package com.quane.little.language

import scala.collection.mutable

class Runtime
  extends Scope(None) {

  private val functions = mutable.Map[String, FunctionDefinition]()

  /** Returns the current runtime; always this.
    *
    * @return the current runtime, this.
    */
  override def runtime = this

  def saveFunction(function: FunctionDefinition) =
    functions += (function.name -> function)

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

  def containsFunction(name: String) = functions.contains(name)

}
