package com.quane.little.ide.view.html

import com.quane.little.ide.presenter.{FunctionParameterPresenter, BlockPresenter}
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

  private val stepList = new BlockLayout

  private val header = createHeader()
  private val body = createBody()
  private val footer = createFooter()

  setSpacing(false)
  addComponent(header)
  addComponent(body)
  addComponent(footer)

  override def setName(name: String): Unit = header.name_=(name)

  private def createHeader() = new FunctionDefinitionHeader(this)

  private def createBody(): Component = {
    val body = new HorizontalLayout
    val bodyLeft = new Label
    bodyLeft.setHeight(100, Sizeable.Unit.PERCENTAGE)
    bodyLeft.setStyleName(FunctionDefinitionLayout.StyleHeadLeft)
    body.addComponent(bodyLeft)
    body.addComponent(stepList)
    body.setStyleName(FunctionDefinitionLayout.StyleBody)
    body.setSpacing(false)
    body
  }

  private def createFooter(): Component = {
    val footer = new CssLayout()
    footer.setStyleName(FunctionDefinitionLayout.StyleFoot)
    footer
  }

  override def createFunctionParameter(): FunctionParameterPresenter[_] = {
    val view = new FunctionParameterComponent()
    header.parameters_+=(view)
    new FunctionParameterPresenter(view)
  }

  override def createBlock(): BlockPresenter[_] = {
    // TODO this goes against the pattern, we should create the block view here
    new BlockPresenter(stepList)
  }

  private[html] def onNameChange(name: String): Unit = presenter.onNameChange(name)

  private[html] def requestNewParameter(): Unit = presenter.onParamAdded(createFunctionParameter())

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
        view.onNameChange(event.getText)
    })
    setStyleName(FunctionDefinitionLayout.StyleHeadNameField)
    // TODO should be a label that, when clicked, becomes a text field
  }

  private def createParameterLayout(): Layout = new HorizontalLayout {
    setSpacing(true)
  }

  private def createNewParameterButton() = Buttons.blueButton("+", view.requestNewParameter)

  private def createSaveButton() = Buttons.blueButton("Save", view.save)

  def name_=(n: String) = nameField.setValue(n)

  def parameters_+=(p: FunctionParameterComponent) = parameterLayout.addComponent(p)

}