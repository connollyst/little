package com.quane.little.ide.view.html

import com.quane.little.ide.view.html.dnd.ExpressionDropHandler
import com.quane.little.ide.view.{EvaluableCodeView, SetterView, SetterViewPresenter}
import com.quane.vaadin.scala.{DroppableTarget, VaadinMixin}
import com.vaadin.event.FieldEvents.{TextChangeEvent, TextChangeListener}
import com.vaadin.ui.{CssLayout, HorizontalLayout, Label, TextField}

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
  private val valueWrapper = new DroppableTarget(
    new CssLayout,
    new ExpressionDropHandler[SetterViewPresenter, SetterView](this)
  )

  setStyleNames(ExpressionLayout.Style, SetterLayout.Style)
  setSpacing(true)

  addComponent(nameField)
  addComponent(new Label("="))
  // TODO add the CodeMenuLayout wrapper here
  addComponent(valueWrapper)
  addComponent(Buttons.closeButton(this))

  override def setName(name: String): Unit = nameField.setValue(name)

  private def createNameTextField() = new TextField {
    setInputPrompt("variable name")
    addTextChangeListener(new TextChangeListener {
      def textChange(event: TextChangeEvent) = presenter.onNameChange(event.getText)
    })
  }

  override def createGetterExpression() = setValueComponent(new GetterLayout)

  override def createMathExpression() = setValueComponent(new MathLayout)

  override def createLogicExpression() = setValueComponent(new LogicLayout)

  override def createValueExpression() = setValueComponent(new ValueLayout)

  override def createFunctionReference() = setValueComponent(new FunctionReferenceLayout)

  private def setValueComponent[T <: EvaluableCodeView[_] with RemovableComponent](view: T): T = {
    valueWrapper.component.removeAllComponents()
    valueWrapper.component.addComponent(view)
    view
  }

}