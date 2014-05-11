package com.quane.little.ide.view.html

import com.quane.little.ide.view.{ExpressionView, SetStatementView}
import com.quane.little.ide.presenter.{FunctionReferencePresenter, GetStatementPresenter, ValuePresenter}
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
  addComponent(CloseButton(this))

  override def setName(name: String): Unit = nameField.setValue(name)

  private def createNameTextField() = new TextField {
    setInputPrompt("variable name")
    addTextChangeListener(new TextChangeListener {
      def textChange(event: TextChangeEvent) = presenter.onNameChange(event.getText)
    })
  }

  override def createValueStatement(): ValuePresenter[ValueLayout] = {
    removeValueComponent()
    val view = new ValueLayout
    valueComponent = Some(view)
    addComponent(view)
    new ValuePresenter(view)
  }

  override def createGetStatement(): GetStatementPresenter[GetStatementLayout] = {
    removeValueComponent()
    val view = new GetStatementLayout
    valueComponent = Some(view)
    addComponent(view)
    new GetStatementPresenter(view)
  }

  override def createFunctionReference(): FunctionReferencePresenter[FunctionReferenceLayout] = {
    removeValueComponent()
    val view = new FunctionReferenceLayout
    valueComponent = Some(view)
    addComponent(view)
    new FunctionReferencePresenter(view)
  }

  private def removeValueComponent(): Unit = {
    valueComponent match {
      case Some(v) => v.removeFromParent()
      case None => // do nothing
    }
    valueComponent = None
  }

}