package com.quane.little.ide.view.html

import com.quane.little.ide.view.{ExpressionView, SetterView}
import com.vaadin.ui.{TextField, HorizontalLayout, Label}
import com.vaadin.event.FieldEvents.{TextChangeListener, TextChangeEvent}
import com.quane.vaadin.scala.VaadinMixin
import scala.Some

object SetterLayout {
  val Style = "l-set"
}

/** An HTML layout view representing a variable assignment expression, a 'setter'.
  *
  * @author Sean Connolly
  */
class SetterLayout
  extends HorizontalLayout
  with SetterView
  with RemovableComponent
  with VaadinMixin {

  private val nameField = createNameTextField()
  private var valueComponent: Option[ExpressionView[_] with RemovableComponent] = None

  setStyleNames(ExpressionLayout.Style, SetterLayout.Style)
  setSpacing(true)

  addComponent(nameField)
  addComponent(new Label("="))
  addComponent(new ExpressionMenu(this))
  addComponent(Buttons.closeButton(this))

  override def setName(name: String): Unit = nameField.setValue(name)

  private def createNameTextField() = new TextField {
    setInputPrompt("variable name")
    addTextChangeListener(new TextChangeListener {
      def textChange(event: TextChangeEvent) = presenter.onNameChange(event.getText)
    })
  }

  override def createValueStatement() = setValueComponent(new ValueLayout)

  override def createGetStatement() = setValueComponent(new GetterLayout)

  override def createFunctionReference() = setValueComponent(new FunctionReferenceLayout)

  private def setValueComponent[T <: ExpressionView[_] with RemovableComponent](view: T): T = {
    removeValueComponent()
    valueComponent = Some(view)
    add(view)
  }

  private def removeValueComponent(): Unit = {
    valueComponent match {
      case Some(v) => v.removeFromParent()
      case None => // do nothing
    }
    valueComponent = None
  }

}