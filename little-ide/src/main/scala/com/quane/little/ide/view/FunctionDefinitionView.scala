package com.quane.little.ide.view

import com.quane.little.ide.presenter.ExpressionPresenter
import com.quane.little.ide.presenter.FunctionDefinitionPresenter
import com.quane.little.ide.presenter.FunctionReferencePresenter

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

  def apply(presenter: FunctionDefinitionPresenter, name: String): FunctionDefinitionView = {
    val fun = new FunctionDefinitionView(presenter, name)
    fun.addStep(
      new FunctionReferenceView(
        new FunctionReferencePresenter,
        "point toward",
        new FunctionArgumentView("x"),
        new FunctionArgumentView("y")))
    fun.addStep(
      new FunctionReferenceView(
        new FunctionReferencePresenter,
        "move",
        new FunctionArgumentView("speed")))
    val ifElse = new ConditionalView("touching [location]")
    ifElse.addThen(new PrintView("done"))
    ifElse.addElse(
      new FunctionReferenceView(
        new FunctionReferencePresenter,
        "move toward",
        new FunctionArgumentView("x"),
        new FunctionArgumentView("y")))
    fun.stepList.add(ifElse)
    fun.stepList.add(new PrintView("done"))
    fun
  }
}

class FunctionDefinitionView(val presenter: FunctionDefinitionPresenter, name: String)
  extends VerticalLayout {

  private val stepList = new ExpressionListView

  spacing = false
  add(header())
  add(body())
  add(footer())

  private def header(): Component = {
    new FunctionDefinitionViewHeader(name)
  }

  private def body(): Component = {
    val body = new HorizontalLayout
    val bodyLeft = new Label
    bodyLeft.height = new Measure(100, Units.pct)
    bodyLeft.styleName = FunctionDefinitionView.StyleHeadLeft
    body.addComponent(bodyLeft)
    body.addComponent(stepList)
    body.styleName = FunctionDefinitionView.StyleBody
    body.spacing = false
    body
  }

  private def footer(): Component = {
    val footer = new CssLayout()
    footer.styleName = FunctionDefinitionView.StyleFoot
    footer
  }

  def addStep(view: ExpressionView[ExpressionPresenter]) = {
    stepList.add(view)
    val nc = view.presenter
    presenter.addStep(nc)
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
      header.add(new FunctionParameterView, children - 1)
      () // how do I avoid this?
    })
    addComponent(addArgumentButton)
  }

}