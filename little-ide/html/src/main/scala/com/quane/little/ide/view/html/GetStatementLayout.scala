package com.quane.little.ide.view.html

import com.quane.little.ide.view.GetStatementView
import com.vaadin.ui.{TextField, HorizontalLayout}
import com.vaadin.event.FieldEvents.{TextChangeEvent, TextChangeListener}
import com.quane.vaadin.scala.VaadinMixin

object GetStatementLayout {
  val Style = "l-get"
  val StyleValue = Style + "-value"
}

/** An HTML layout view representing a variable reference expression, a 'getter'.
  *
  * @author Sean Connolly
  */
class GetStatementLayout
  extends HorizontalLayout
  with GetStatementView
  with RemovableComponent
  with VaadinMixin {

  private val nameField = createNameField()

  setStyleNames(ExpressionLayout.Style, GetStatementLayout.Style)
  nameField.setStyleName(GetStatementLayout.StyleValue)

  addComponent(nameField)

  override def setName(name: String): Unit = nameField.setValue(name)

  private def createNameField() = new TextField {
    setInputPrompt("variable name")
    addTextChangeListener(new TextChangeListener {
      def textChange(event: TextChangeEvent) = presenter.onNameChange(event.getText)
    })
  }

}
