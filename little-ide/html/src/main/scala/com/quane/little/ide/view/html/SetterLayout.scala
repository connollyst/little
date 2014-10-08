package com.quane.little.ide.view.html

import com.quane.little.ide.view.html.dnd.CodeDropHandler
import com.quane.little.ide.view.{CodeView, SetterView, SetterViewPresenter}
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
    new CodeDropHandler[SetterViewPresenter, SetterView](this)
  )

  setStyleNames(CodeLayout.Style, SetterLayout.Style)
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

  override def createGetterView() = setValueComponent(new GetterLayout)

  override def createMathView() = setValueComponent(new MathLayout)

  override def createLogicView() = setValueComponent(new LogicLayout)

  override def createValueView() = setValueComponent(new ValueLayout)

  override def createFunctionReferenceView() = setValueComponent(new FunctionReferenceLayout)

  private def setValueComponent[T <: CodeView[_] with RemovableComponent](view: T): T = {
    valueWrapper.component.removeAllComponents()
    valueWrapper.component.addComponent(view)
    view
  }

}