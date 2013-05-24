package com.quane.little.ide.controls

import com.quane.little.language.Scope
import com.quane.little.language.memory.Pointer
import javafx.scene.control.TextField
import com.quane.little.language.data.{Text, Bool, Number, ValueTypeSafe}

abstract class PointerField[V <: ValueTypeSafe](valueClass: Class[V])
  extends TextField {

  def compile(scope: Scope): Pointer[V] = {
    new Pointer(scope, getText, valueClass)
  }

}

class BoolPointerField
  extends PointerField[Bool](classOf[Bool])

class NumberPointerField
  extends PointerField[Number](classOf[Number])

class TextPointerField
  extends PointerField[Text](classOf[Text])
