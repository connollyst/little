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

  /** Save the given function definition to this runtime.<br/>
    * Once added, a function cannot be redefined.
    *
    * @param function the function definition to store in the runtime
    * @throws IllegalAccessException if a function of this name is already defined
    */
  def saveFunction(function: FunctionDefinition): Unit =
    if (containsFunction(function.name)) {
      throw new IllegalAccessException(
        "Function '" + function.name + "' already defined in runtime " + this
      )
    } else {
      functions += (function.name -> function)
    }

  /** Returns the named [[com.quane.little.language.FunctionDefinition]].
    *
    * @param name the name of the function
    * @return the function, if defined
    * @throws RuntimeException if no function with this name is defined
    */
  def fetchFunction(name: String): FunctionDefinition = {
    if (functions.contains(name)) {
      functions(name)
    } else {
      // TODO what to do?
      throw new RuntimeException("No function '" + name + "' in runtime " + this)
    }
  }

  def containsFunction(name: String) = functions.contains(name)

}
