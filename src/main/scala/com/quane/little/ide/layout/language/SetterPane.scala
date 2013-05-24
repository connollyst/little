package com.quane.little.ide.layout.language

import com.quane.little.language.{Scope, SetStatement}
import com.quane.little.ide.CustomControl
import javafx.fxml.FXML
import javafx.scene.control.TextField
import com.quane.little.language.data.Value
import javafx.scene.layout.HBox

/**
 *
 * @author Sean Connolly
 */
class SetterPane
  extends HBox
  with ExpressionPane[SetStatement[_ <: Value]]
  with CustomControl {

  def fxml: String = "SetterPane.fxml"

  @FXML
  private var a: TextField = _
  @FXML
  private var b: TextField = _

  override def compile(scope: Scope): SetStatement[_ <: Value] = {
//    new Pointer[_ <: Value]()
    null
    //    new SetStatement[Any]()
  }


}
