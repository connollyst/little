package com.quane.little.ide.view.html

import com.quane.little.ide.presenter.{FunctionParameterPresenter, ExpressionPresenter}
import vaadin.scala._
import com.quane.little.ide.view.FunctionDefinitionView


object FunctionDefinitionComponent {
  val Style = "l-function-def"
  val StyleBody = Style + "-body"
  val StyleHeadLeft = StyleBody + "-left"
  val StyleFoot = Style + "-foot"
  val StyleHead = Style + "-head"
  val StyleHeadNameField = StyleHead + "-name"
}

class FunctionDefinitionComponent
extends VerticalLayout with FunctionDefinitionView {

  val stepList = new ExpressionListComponent

  private val header = createHeader()
  private val body = createBody()
  private val footer = createFooter()

  spacing = false
  add(header)
  add(body)
  add(footer)

  private def createHeader(): FunctionDefinitionHeader = {
    new FunctionDefinitionHeader(this)
  }

  private def createBody(): Component = {
    val body = new HorizontalLayout
    val bodyLeft = new Label
    bodyLeft.height = new Measure(100, Units.pct)
    bodyLeft.styleName = FunctionDefinitionComponent.StyleHeadLeft
    body.addComponent(bodyLeft)
    body.addComponent(stepList)
    body.styleName = FunctionDefinitionComponent.StyleBody
    body.spacing = false
    body
  }

  private def createFooter(): Component = {
    val footer = new CssLayout()
    footer.styleName = FunctionDefinitionComponent.StyleFoot
    footer
  }


  override def setName(name: String): Unit = {
    header.name_=(name)
  }

  override def createFunctionParameter(): FunctionParameterPresenter[_] = {
    val view = new FunctionParameterComponent()
    // TODO add parameter to view
    new FunctionParameterPresenter(view)
  }

  def addStep(view: ExpressionView[ExpressionPresenter]): FunctionDefinitionComponent = {
    stepList.add(view)
    this
  }

}

private class FunctionDefinitionHeader(val definition: FunctionDefinitionComponent)
  extends HorizontalLayout {

  // TODO should be a label that, when clicked, becomes a text field

  private val nameField = createNameField()
  private val parameterLayout = createParameterLayout()
  private val newParameterButton = createNewParameterButton()
  private val saveButton = createSaveButton()

  styleName = FunctionDefinitionComponent.StyleHead
  spacing = true
  add(nameField)
  add(parameterLayout)
  add(newParameterButton)
  add(saveButton)

  private def createNameField(): TextField = {
    val nameField = new TextField
    nameField.styleName = FunctionDefinitionComponent.StyleHeadNameField
    nameField
  }

  private def createParameterLayout(): Layout = {
    val paramLayout = new HorizontalLayout
    paramLayout.spacing = true
    paramLayout
  }

  private def createNewParameterButton(): Component = Button(
  "+", {
    val header = FunctionDefinitionHeader.this
    val children = header.components.size
    header.add(new FunctionParameterComponent, children - 1)
    () // how do I avoid this?
  })

  private def createSaveButton(): Component = Button(
  "Save", {
    val header = FunctionDefinitionHeader.this
    val compiled = header.definition.compile()
    println("Compiled: " + compiled)
    () // how do I avoid this?
  })

  def name_=(n: String) = {
    nameField.value = n
  }

  def parameters_+=(p: FunctionParameterComponent) = {
    println("Adding function parameter UI: " + p.value)
    parameterLayout.add(p)
  }

}