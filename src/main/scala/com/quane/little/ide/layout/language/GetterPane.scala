package com.quane.little.ide.layout.language

import javafx.scene.layout.HBox
import com.quane.little.language.{Scope, GetStatement}
import com.quane.little.ide.CustomControl
import org.eintr.loglady.Logging
import javafx.fxml.FXML
import com.quane.little.ide.controls.PointerField

class GetterPane
  extends HBox
  with ExpressionPane[GetStatement]
  with CustomControl
  with Logging {

  def fxml: String = "GetterPane.fxml"

  @FXML
  private var pointer: PointerField = _

  /** Generate the [[com.quane.little.language.Expression]] corresponding to
    * this [[com.quane.little.ide.layout.language.ExpressionPane]] in the
    * given [[com.quane.little.language.Scope]].
    *
    * @param scope the parent scope of the expression
    * @return the expression
    */
  def compile(scope: Scope): GetStatement = {
    new GetStatement(pointer.compile(scope))
  }

}
