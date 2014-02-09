package com.quane.little.ide.view

import com.quane.little.ide.controller.{FunctionReferenceController, ExpressionController, FunctionDefinitionController}
import vaadin.scala.Button
import vaadin.scala.Component
import vaadin.scala.CssLayout
import vaadin.scala.HorizontalLayout
import vaadin.scala.Label
import vaadin.scala.Measure
import vaadin.scala.TextField
import vaadin.scala.Units
import vaadin.scala.VerticalLayout

object FunctionDefinitionView {
  val Style = "l-function-def"
  val StyleBody = Style + "-body"
  val StyleHeadLeft = StyleBody + "-left"
  val StyleFoot = Style + "-foot"
  val StyleHead = Style + "-head"
  val StyleHeadNameField = StyleHead + "-name"
}

class FunctionDefinitionView(val controller: FunctionDefinitionController, name: String)
  extends VerticalLayout {

  val stepList = new ExpressionListView

  spacing = false
  add(header())
  add(body())
  add(footer())

  def header(): Component = {
    new FunctionDefinitionViewHeader(name)
  }

  def body(): Component = {
    val body = new HorizontalLayout
    val bodyLeft = new Label
    bodyLeft.height = new Measure(100, Units.pct)
    bodyLeft.styleName = FunctionDefinitionView.StyleHeadLeft
    body.addComponent(bodyLeft)
    body.addComponent(stepList)
    body.styleName = FunctionDefinitionView.StyleBody
    body.spacing = false
    initMockData()
    body
  }

  def footer(): Component = {
    val footer = new CssLayout()
    footer.styleName = FunctionDefinitionView.StyleFoot
    footer
  }

  def addStep(view: ExpressionView[ExpressionController]) = {
    stepList.add(view)
    val nc = view.controller
    controller.addStep(nc)
  }

  def initMockData() = {
    addStep(
      new FunctionReferenceView(
        new FunctionReferenceController,
        "point toward",
        new FunctionArgumentView("x"),
        new FunctionArgumentView("y")))
    addStep(
      new FunctionReferenceView(
        new FunctionReferenceController,
        "move",
        new FunctionArgumentView("speed")))
    val ifElse = new ConditionalView("touching [location]")
    ifElse.addThen(new PrintView("done"))
    ifElse.addElse(
      new FunctionReferenceView(
        new FunctionReferenceController,
        "move toward",
        new FunctionArgumentView("x"),
        new FunctionArgumentView("y")))
    stepList.add(ifElse)
    stepList.add(new PrintView("done"))
  }
}

class FunctionDefinitionViewHeader(name: String) extends HorizontalLayout {

  // TODO should be a label that, when clicked, becomes a text field

  def this() {
    this("")
  }

  styleName = FunctionDefinitionView.StyleHead
  spacing = true
  initNameField
  initAddArgumentButton

  def initNameField = {
    val nameField = new TextField
    nameField.value = name
    nameField.styleName = FunctionDefinitionView.StyleHeadNameField
    addComponent(nameField)
  }

  def initAddArgumentButton = {

    val addArgumentButton = Button(
    "+", {
      val header = FunctionDefinitionViewHeader.this
      val children = header.components.size
      header.add(new FunctionParameter, children - 1)
      () // how do I avoid this?
    })
    addComponent(addArgumentButton)
  }

}