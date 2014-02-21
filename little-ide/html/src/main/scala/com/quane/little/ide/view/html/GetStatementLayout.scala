package com.quane.little.ide.view.html

import com.quane.little.ide.view.GetStatementView
import vaadin.scala.HorizontalLayout
import vaadin.scala.TextField
import vaadin.scala.AbstractTextField

object GetStatementLayout {
  val Style = "l-get"
  val StyleValue = Style + "-value"
}

/** HTML view for get statements.
  *
  * @author Sean Connolly
  */
class GetStatementLayout
  extends HorizontalLayout
  with GetStatementView {

  private val nameField = new TextField {
    textChangeListeners += {
      e: AbstractTextField.TextChangeEvent =>
        viewListeners.foreach {
          listener => listener.nameChanged(e.text)
        }
    }
  }

  add(nameField)
  styleName = ExpressionLayout.Style + " " + GetStatementLayout.Style
  nameField.styleName = GetStatementLayout.StyleValue

  def setName(name: String): Unit = nameField.value = name

}
