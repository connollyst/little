package com.quane.little.ide.layout.language

import com.quane.little.language.{Scope, SetStatement}
import com.quane.little.ide.CustomControl
import javafx.fxml.FXML
import javafx.scene.layout.HBox
import org.eintr.loglady.Logging
import com.quane.little.ide.controls.{ValueField, PointerField}

class SetterPane
  extends HBox
  with ExpressionPane[SetStatement]
  with CustomControl
  with Logging {

  def fxml: String = "SetterPane.fxml"

  @FXML
  private var a: PointerField = _
  @FXML
  private var b: ValueField = _

  override def compile(scope: Scope): SetStatement = {
    log.info("Compiling: " + a.getText + " = " + b.getText)
    new SetStatement(a.compile(scope), b.compile(scope))
  }

}
