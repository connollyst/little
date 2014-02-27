package com.quane.little.ide.view.html

import com.quane.little.ide.presenter.BlockPresenter
import com.quane.little.ide.presenter.FunctionParameterPresenter
import com.quane.little.ide.presenter.FunctionReferencePresenter
import com.quane.little.ide.view.FunctionDefinitionView

import vaadin.scala.Button
import vaadin.scala.Component
import vaadin.scala.CssLayout
import vaadin.scala.HorizontalLayout
import vaadin.scala.Label
import vaadin.scala.Layout
import vaadin.scala.Measure
import vaadin.scala.TextField
import vaadin.scala.Units
import vaadin.scala.VerticalLayout
import vaadin.scala.AbstractTextField.TextChangeEvent

object FunctionDefinitionLayout {
  val Style = "l-function-def"
  val StyleBody = Style + "-body"
  val StyleHeadLeft = StyleBody + "-left"
  val StyleFoot = Style + "-foot"
  val StyleHead = Style + "-head"
  val StyleHeadNameField = StyleHead + "-name"
}

class FunctionDefinitionLayout
  extends VerticalLayout
  with FunctionDefinitionView
  with HtmlComponent {

  val stepList = new BlockLayout

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
    bodyLeft.styleName = FunctionDefinitionLayout.StyleHeadLeft
    body.add(bodyLeft)
    body.add(stepList)
    body.styleName = FunctionDefinitionLayout.StyleBody
    body.spacing = false
    body
  }

  private def createFooter(): Component = {
    val footer = new CssLayout()
    footer.styleName = FunctionDefinitionLayout.StyleFoot
    footer
  }

  override def setName(name: String): Unit = header.name_=(name)

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
    val view = new FunctionReferenceComponent()
    // TODO replace stepList with body
    stepList.add(view)
    new FunctionReferencePresenter(view)
  }

  def onNameChange(name: String): Unit = {
    viewListeners.foreach {
      listener => listener.onNameChange(name)
    }
  }

  private[html] def requestNewParameter(): Unit = {
    viewListeners.foreach {
      listener => listener.addParameter(createFunctionParameter())
    }
  }

}

private class FunctionDefinitionHeader(val definition: FunctionDefinitionLayout)
  extends HorizontalLayout {

  // TODO should be a label that, when clicked, becomes a text field

  private val nameField = createNameField()
  private val parameterLayout = createParameterLayout()
  private val newParameterButton = createNewParameterButton()
  private val saveButton = createSaveButton()

  styleName = FunctionDefinitionLayout.StyleHead
  spacing = true
  add(nameField)
  add(parameterLayout)
  add(newParameterButton)
  add(saveButton)

  private def createNameField(): TextField = new TextField {
    textChangeListeners += {
      e: TextChangeEvent =>
        definition.onNameChange(e.text)
    }
    styleName = FunctionDefinitionLayout.StyleHeadNameField
  }

  private def createParameterLayout(): Layout = new HorizontalLayout {
    spacing = true
  }

  private def createNewParameterButton(): Component = Button(
  "+", {
    definition.requestNewParameter()
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
    parameterLayout.add(p)
  }

}