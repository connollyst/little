package com.quane.little.ide.controls

import com.quane.little.language.data._
import javafx.scene.control.TextField
import com.quane.little.ide.layout.language.ExpressionPane
import com.quane.little.language.{Expression, Scope}
import javafx.scene.layout.HBox
import com.quane.little.ide.CustomControl
import com.quane.little.ide.dnd.{GetterToolType, DragAndDropItem, DnDTarget}
import javafx.fxml.FXML

/** A value field control wraps two different methods for providing a
  * [[com.quane.little.language.data.Value]].
  *
  * One approach is to provide a value in a simple text field as it. This
  * would be the method to, say, set the value of a variable to '10' or
  * 'Hello World'.
  *
  * The other approach is to use an
  * [[com.quane.little.ide.layout.language.ExpressionPane]] which compiles
  * down to a [[com.quane.little.language.data.Value]]. This could be a
  * [[com.quane.little.ide.layout.language.GetterPane]] or a reference to a
  * [[com.quane.little.language.Function]] perhaps.
  *
  * @author Sean Connolly
  */
class ValueField
  extends HBox
  with ExpressionPane[Expression[Value]]
  with CustomControl
  with DnDTarget {

  override def fxml: String = "ValueField.fxml"

  @FXML
  private var textField: TextField = _
  private var expressionPane: ExpressionPane[Expression[Value]] = _

  /** Returns the text value of the field.
    *
    * @return the text value
    */
  def getText: String = {
    if (textField.isVisible) {
      textField.getText
    } else {
      expressionPane.toString
    }
  }

  /** Can the item be dropped here?
    *
    * @param item the drag and drop item
    */
  def accepts(item: DragAndDropItem): Boolean = item match {
    case GetterToolType => true
    case _ => false
  }


  /** Handle a new [[com.quane.little.ide.layout.language.ExpressionPane]]
    * being dropped.
    *
    * @param pane the pane that was dropped
    */
  override def onDrop(pane: ExpressionPane[Expression[Any]]) {
    // TODO can the compiler help us here?
    expressionPane = pane.asInstanceOf[ExpressionPane[Expression[Value]]]
    getChildren.add(expressionPane)
    textField.managedProperty().bind(visibleProperty())
    textField.setVisible(false)
  }

  /** Generate the [[com.quane.little.language.Expression]] corresponding to
    * this [[com.quane.little.ide.layout.language.ExpressionPane]] in the
    * given [[com.quane.little.language.Scope]].
    *
    * @param scope the parent scope of the expression
    * @return the expression
    */
  def compile(scope: Scope): Expression[Value] = {
    if (textField.isVisible) {
      new Value(textField.getText)
    } else {
      expressionPane.compile(scope)
    }
  }

}