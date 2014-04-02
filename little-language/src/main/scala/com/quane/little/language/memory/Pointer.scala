package com.quane.little.language.memory

import com.quane.little.language.Scope
import com.quane.little.language.data.{Value, Variable}

/** Represents a pointer to a variable in memory.<br/>
  * Of course, this is not a real pointer to real memory. Instead, it is used
  * as a 'key' of sorts. Without a Pointer, we cannot <i>get</i> or <i>set</i>
  * a [[com.quane.little.language.data.Variable]] in memory and thus it allows
  * us to assert the validity of a Variable at runtime.
  *
  * @param variableName the name of the variable in memory, unique in scope
  *
  * @author Sean Connolly
  */
class Pointer(val variableName: String) {

  // TODO if the constructor requires a value, we can avoid nulls!

  def resolve(scope: Scope): Variable = scope.fetch(variableName)

  def update(scope: Scope, value: Value) = scope.save(variableName, value)

}