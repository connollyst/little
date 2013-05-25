package com.quane.little.ide.controls

import com.quane.little.language.data._
import javafx.scene.control.TextField
import com.quane.little.ide.layout.language.ExpressionPane
import com.quane.little.language.{Expression, Scope}

class ValueField
  extends TextField
  with ExpressionPane[Expression[Value]] {

  def compile(scope: Scope): Expression[Value] = new Value(getText)

}