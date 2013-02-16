package com.quane.glass.language.memory

import com.quane.glass.language.data.Value
import com.quane.glass.language.Scope

/** Represents a pointer to a variable in memory.<br/>
  * Of course, this is not a real pointer to real memory. Instead, it is used
  * as a 'key' of sorts. Without a Pointer, we cannot <i>get</i> or <i>set</i>
  * a {@link Variable} in memory and thus it allows us to assert the validity
  * of a {@link Variable} at runtime.
  *
  * @param scope
  * 		the scope of the variable
  * @param variableName
  * 		the name of the variable in memory, unique in scope
  * @param valueClass
  * 		the Class of the stored value (note: we are strongly typed)
  *
  * @author Sean Connolly
  */
class Pointer[V <: Value](val scope: Scope, val variableName: String, val valueClass: Class[V])

// TODO if the constructor requires a value, we can avoid nulls!