package com.quane.little.ide.controls

import com.quane.little.language.Scope
import com.quane.little.language.memory.Pointer
import javafx.scene.control.TextField

class PointerField
  extends TextField {

  def compile(scope: Scope): Pointer = {
    new Pointer(scope, getText)
  }

}
