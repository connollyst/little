package com.quane.little.ide.view.html

import com.quane.little.ide.view.{ExpressionView, SetterView}
import com.vaadin.ui.{CssLayout, TextField, HorizontalLayout, Label}
import com.vaadin.event.FieldEvents.{TextChangeListener, TextChangeEvent}
import com.quane.vaadin.scala.{DroppableTarget, VaadinMixin}
import com.vaadin.event.dd.{DragAndDropEvent, DropHandler}
import com.vaadin.event.dd.acceptcriteria.AcceptAll
import com.quane.little.ide.view.html.dnd.CodeTransferable
import com.quane.little.data.model.CodeCategory

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
  private val valueWrapper = new DroppableTarget(new CssLayout, new ArgumentDropHandler(this))

  setStyleNames(ExpressionLayout.Style, SetterLayout.Style)
  setSpacing(true)

  addComponent(nameField)
  addComponent(new Label("="))
  addComponent(new ExpressionMenu(this))
  addComponent(valueWrapper)
  addComponent(Buttons.closeButton(this))

  override def setName(name: String): Unit = nameField.setValue(name)

  private def createNameTextField() = new TextField {
    setInputPrompt("variable name")
    addTextChangeListener(new TextChangeListener {
      def textChange(event: TextChangeEvent) = presenter.onNameChange(event.getText)
    })
  }

  override def createValueExpression() = setValueComponent(new ValueLayout)

  override def createGetExpression() = setValueComponent(new GetterLayout)

  override def createFunctionReference() = setValueComponent(new FunctionReferenceLayout)

  private def setValueComponent[T <: ExpressionView[_] with RemovableComponent](view: T): T = {
    valueWrapper.component.removeAllComponents()
    valueWrapper.component.addComponent(view)
    view
  }

}

private class ArgumentDropHandler(view: SetterLayout) extends DropHandler {

  override def getAcceptCriterion = AcceptAll.get()

  override def drop(event: DragAndDropEvent) =
    event.getTransferable match {
      case transferable: CodeTransferable if transferable.category == CodeCategory.Expression =>
        view.presenter.requestAddExpression(transferable.codeId, 0)
      case transferable: CodeTransferable if transferable.category == CodeCategory.Function =>
        view.presenter.requestAddFunctionReference(transferable.codeId, 0)
      case _ =>
        throw new IllegalAccessException("Drop not supported: " + event.getTransferable)
    }

}