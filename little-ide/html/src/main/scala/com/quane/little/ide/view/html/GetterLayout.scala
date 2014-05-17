package com.quane.little.ide.view.html

import com.quane.little.ide.view.GetterView
import com.vaadin.ui.{TextField, HorizontalLayout}
import com.vaadin.event.FieldEvents.{TextChangeEvent, TextChangeListener}
import com.quane.vaadin.scala.VaadinMixin

object GetterLayout {
  val Style = "l-get"
  val StyleValue = Style + "-value"
}

/** An HTML layout view representing a variable reference expression, a 'getter'.
  *
  * @author Sean Connolly
  */
class GetterLayout
  extends HorizontalLayout
  with GetterView
  with RemovableComponent
  with VaadinMixin {

  private val nameField = createNameField()

  setStyleNames(ExpressionLayout.Style, GetterLayout.Style)
  nameField.setStyleName(GetterLayout.StyleValue)

  addComponent(nameField)

  override def setName(name: String): Unit = nameField.setValue(name)

  private def createNameField() = new TextField {
    setInputPrompt("variable name")
    addTextChangeListener(new TextChangeListener {
      def textChange(event: TextChangeEvent) = presenter.onNameChange(event.getText)
    })
  }

}
