package com.quane.little.ide.layout.language

import com.quane.little.language.{Scope, SetStatement}
import com.quane.little.ide.CustomControl
import javafx.fxml.FXML
import com.quane.little.language.data.{ValueTypeSafe, Bool, Number, Text}
import javafx.scene.layout.HBox
import org.eintr.loglady.Logging
import com.quane.little.ide.controls.{ValueField, PointerField}

abstract class SetterPane[V <: ValueTypeSafe]
  extends HBox
  with ExpressionPane[SetStatement[V]]
  with CustomControl
  with Logging {

  @FXML
  private var a: PointerField[V] = _
  @FXML
  private var b: ValueField[V] = _

  override def compile(scope: Scope): SetStatement[V] = {
    log.info("Compiling: " + a.getText + " = " + b.getText)
    new SetStatement(a.compile(scope), b.compile(scope))
  }

}

class BoolSetterPane
  extends SetterPane[Bool] {

  def fxml: String = "BoolSetterPane.fxml"

}