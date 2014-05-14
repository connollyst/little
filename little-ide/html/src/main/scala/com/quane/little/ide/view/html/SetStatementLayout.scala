package com.quane.little.ide.view.html

import com.quane.little.ide.view.{ExpressionView, SetStatementView}
import com.vaadin.ui.{TextField, HorizontalLayout, Label}
import com.vaadin.event.FieldEvents.{TextChangeListener, TextChangeEvent}
import com.quane.vaadin.scala.VaadinMixin
import scala.Some

object SetStatementLayout {
  val Style = "l-set"
}

/** An HTML layout view representing a variable assignment expression, a 'setter'.
  *
  * @author Sean Connolly
  */
class SetStatementLayout
  extends HorizontalLayout
  with SetStatementView
  with RemovableComponent
  with VaadinMixin {

  private val nameField = createNameTextField()
  private var valueComponent: Option[ExpressionView[_] with RemovableComponent] = None

  setStyleNames(ExpressionLayout.Style, SetStatementLayout.Style)
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

  override def createValueStatement(): ValueLayout = {
    removeValueComponent()
    val view = new ValueLayout
    valueComponent = Some(view)
    add(view)
  }

  override def createGetStatement(): GetStatementLayout = {
    removeValueComponent()
    val view = new GetStatementLayout
    valueComponent = Some(view)
    add(view)
  }

  override def createFunctionReference(): FunctionReferenceLayout = {
    removeValueComponent()
    val view = new FunctionReferenceLayout
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