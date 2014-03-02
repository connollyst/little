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
  with GetStatementView
  with HtmlComponent
  with CloseableComponent {

  private val nameField = createNameField()

  styleName = ExpressionLayout.Style + " " + GetStatementLayout.Style
  nameField.styleName = GetStatementLayout.StyleValue

  add(nameField)
  add(CloseButton(this))

  override def setName(name: String): Unit = nameField.value = name

  private def createNameField() = new TextField {
    prompt = "variable name"
    textChangeListeners += {
      e: AbstractTextField.TextChangeEvent =>
        viewListeners.foreach {
          listener => listener.onNameChange(e.text)
        }
    }
  }

}
