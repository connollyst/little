package com.quane.little.ide.language.memory

import com.quane.little.language.Scope
import com.quane.little.language.memory.Pointer
import com.quane.little.language.data.Value

/** An IDE controller representing a {@link Pointer}, a reference to a variable
  * in memory.
  *
  * @author Sean Connolly
  */
class PointerPanelController[V <: Value](val view: PointerPanel, valueClass: Class[V]) {

    def compile(scope: Scope): Pointer[V] = {
        new Pointer(scope, view.variableName, valueClass)
    }

}