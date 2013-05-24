package com.quane.little.ide.controls

import com.quane.little.language.data.{Bool, ValueTypeSafe, Number, Text}
import javafx.scene.control.TextField
import com.quane.little.ide.layout.language.ExpressionPane
import com.quane.little.language.{Expression, Scope}

abstract class ValueField[V <: ValueTypeSafe]
  extends TextField
  with ExpressionPane[Expression[V]]

class BoolValueField
  extends ValueField[Bool] {
  def compile(scope: Scope): Expression[Bool] = new Bool(getText.toBoolean)
}

class NumberValueField
  extends ValueField[Number] {
  def compile(scope: Scope): Expression[Number] = new Number(getText.toInt)
}

class TextValueField
  extends ValueField[Text] {
  def compile(scope: Scope): Expression[Text] = new Text(getText)
}