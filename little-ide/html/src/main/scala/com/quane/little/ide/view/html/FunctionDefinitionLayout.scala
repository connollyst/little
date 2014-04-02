package com.quane.little.ide.view.html

import com.quane.little.ide.presenter.BlockPresenter
import com.quane.little.ide.presenter.FunctionParameterPresenter
import com.quane.little.ide.presenter.FunctionReferencePresenter
import com.quane.little.ide.view.FunctionDefinitionView
import com.vaadin.event.FieldEvents.{TextChangeListener, TextChangeEvent}
import com.vaadin.server.Sizeable
import com.vaadin.ui._


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

  private def createHeader(): FunctionDefinitionHeader = {
    new FunctionDefinitionHeader(this)
  }

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

  override def createFunctionReference(): FunctionReferencePresenter[_] = {
    val view = new FunctionReferenceLayout()
    // TODO replace stepList with body
    stepList.addComponent(view)
    new FunctionReferencePresenter(view)
  }

  private[html] def onNameChange(name: String): Unit = {
    _viewPresenter.foreach {
      listener => listener.onNameChange(name)
    }
  }

  private[html] def requestNewParameter(): Unit = {
    _viewPresenter.foreach {
      listener => listener.onParamAdded(createFunctionParameter())
    }
  }

}

private class FunctionDefinitionHeader(val definition: FunctionDefinitionLayout)
  extends HorizontalLayout {

  private val nameField = createNameField()
  private val parameterLayout = createParameterLayout()
  private val newParameterButton = createNewParameterButton()
  private val saveButton = createSaveButton()
  private val closeButton = createCloseButton()

  setStyleName(FunctionDefinitionLayout.StyleHead)
  setSpacing(true)
  addComponent(nameField)
  addComponent(parameterLayout)
  addComponent(newParameterButton)
  addComponent(saveButton)
  addComponent(closeButton)

  private def createNameField(): TextField = new TextField {
    setInputPrompt("function name")
    addTextChangeListener(new TextChangeListener {
      def textChange(event: TextChangeEvent) =
        definition.onNameChange(event.getText)
    })
    setStyleName(FunctionDefinitionLayout.StyleHeadNameField)
    // TODO should be a label that, when clicked, becomes a text field
  }

  private def createParameterLayout(): Layout = new HorizontalLayout {
    setSpacing(true)
  }

  private def createNewParameterButton() = new Button("+",
    new Button.ClickListener {
      def buttonClick(event: Button.ClickEvent) =
        definition.requestNewParameter()
    })

  private def createSaveButton() = new Button("Compile",
    new Button.ClickListener {
      def buttonClick(event: Button.ClickEvent) = {
        val header = FunctionDefinitionHeader.this
        val compiled = header.definition.compile
        println("Compiled: " + compiled)
      }
    })

  private def createCloseButton() = CloseButton(definition)

  def name_=(n: String) = {
    nameField.setValue(n)
  }

  def parameters_+=(p: FunctionParameterComponent) = {
    parameterLayout.addComponent(p)
  }

}