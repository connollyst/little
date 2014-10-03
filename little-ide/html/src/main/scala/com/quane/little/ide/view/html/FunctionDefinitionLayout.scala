package com.quane.little.ide.view.html

import com.quane.little.ide.view.FunctionDefinitionView
import com.vaadin.event.FieldEvents.{TextChangeListener, TextChangeEvent}
import com.vaadin.server.Sizeable
import com.vaadin.ui._
import com.quane.vaadin.scala.VaadinMixin

object FunctionDefinitionLayout {
  val Style = "l-function-def"
  val StyleBody = Style + "-body"
  val StyleHeadLeft = StyleBody + "-left"
  val StyleFoot = Style + "-foot"
  val StyleHead = Style + "-head"
  val StyleHeadNameField = StyleHead + "-name"
}

/** An HTML layout view representing a function definition.
  *
  * @author Sean Connolly
  */
class FunctionDefinitionLayout
  extends VerticalLayout
  with FunctionDefinitionView
  with RemovableComponent {

  private val blockWrapper = new CssLayout with VaadinMixin

  private val header = createHeader()
  private val body = createBody()

  setSpacing(false)
  addComponent(header)
  addComponent(body)

  override def setName(name: String): Unit = header.name_=(name)

  private def createHeader() = new FunctionDefinitionHeader(this)

  private def createBody(): Component = {
    val body = new HorizontalLayout
    val bodyLeft = new Label
    bodyLeft.setStyleName(FunctionDefinitionLayout.StyleHeadLeft)
    body.addComponent(bodyLeft)
    body.addComponent(blockWrapper)
    body.setExpandRatio(blockWrapper, 1)
    body.setStyleName(FunctionDefinitionLayout.StyleBody)
    body.setSizeFull()
    body.setSpacing(false)
    body
  }

  override def createFunctionParameter() = header.add(new FunctionParameterComponent)

  override def createBlock() = blockWrapper.add(new BlockLayout)

  private[html] def requestAddParameter() = presenter.requestAddParameter()

}

private class FunctionDefinitionHeader(view: FunctionDefinitionLayout)
  extends HorizontalLayout
  with VaadinMixin {

  private val nameField = createNameField()
  private val parameterLayout = createParameterLayout()
  private val newParameterButton = createNewParameterButton()
  private val saveButton = createSaveButton()

  setSpacing(true)
  setWidth(100, Sizeable.Unit.PERCENTAGE)
  setDefaultComponentAlignment(Alignment.MIDDLE_LEFT)
  setStyleName(FunctionDefinitionLayout.StyleHead)
  add(nameField)
  add(parameterLayout)
  add(newParameterButton)
  add(saveButton)
  setExpandRatio(parameterLayout, 1f)

  private def createNameField(): TextField = new TextField {
    setInputPrompt("function name")
    addTextChangeListener(new TextChangeListener {
      def textChange(event: TextChangeEvent) =
      // TODO convert to Command
        view.presenter.onNameChange(event.getText)
    })
    setStyleName(FunctionDefinitionLayout.StyleHeadNameField)
    // TODO should be a label that, when clicked, becomes a text field
  }

  private def createParameterLayout() = new HorizontalLayout with VaadinMixin {
    setSpacing(true)
  }

  // TODO convert to Command
  private def createNewParameterButton() = Buttons.blueButton("+", view.requestAddParameter)

  private def createSaveButton() = Buttons.blueButton("Save", view.save)

  def name_=(n: String) = nameField.setValue(n)

  def add(p: FunctionParameterComponent): FunctionParameterComponent = parameterLayout.add(p)

}