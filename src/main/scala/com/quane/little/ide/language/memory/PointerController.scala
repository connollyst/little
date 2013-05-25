package com.quane.little.ide.language.memory

import com.quane.little.language.Scope
import com.quane.little.language.memory.Pointer
import com.quane.little.language.data.ValueTypeSafe

/** An IDE controller representing a {@link Pointer}, a reference to a variable
  * in memory.
  *
  * @author Sean Connolly
  */
class PointerController[V <: ValueTypeSafe](val view: PointerPanel, valueClass: Class[V]) {

    def compile(scope: Scope): Pointer = {
        new Pointer(scope, view.variableName)
    }

}