package com.quane.little.ide.view.html

import com.quane.little.ide.presenter.ExpressionPresenter
import com.quane.little.ide.presenter.FunctionDefinitionPresenter
import com.quane.little.ide.presenter.FunctionReferencePresenter
import vaadin.scala._


object FunctionDefinitionComponent {
  val Style = "l-function-def"
  val StyleBody = Style + "-body"
  val StyleHeadLeft = StyleBody + "-left"
  val StyleFoot = Style + "-foot"
  val StyleHead = Style + "-head"
  val StyleHeadNameField = StyleHead + "-name"

  def apply(presenter: FunctionDefinitionPresenter, name: String): FunctionDefinitionComponent = {
    val fun = new FunctionDefinitionComponent(presenter, name,
      new FunctionParameterComponent("x"),
      new FunctionParameterComponent("y"))
    fun.addStep(
      new FunctionReferenceComponent(
        new FunctionReferencePresenter,
        "point toward",
        new FunctionArgumentComponent("x"),
        new FunctionArgumentComponent("y")))
    fun.addStep(
      new FunctionReferenceComponent(
        new FunctionReferencePresenter,
        "move",
        new FunctionArgumentComponent("speed")))
    val ifElse = new ConditionalComponent("touching [location]")
    ifElse.addThen(new PrintComponent("done"))
    ifElse.addElse(
      new FunctionReferenceComponent(
        new FunctionReferencePresenter,
        "move toward",
        new FunctionArgumentComponent("x"),
        new FunctionArgumentComponent("y")))
    fun.stepList.add(ifElse)
    fun.stepList.add(new PrintComponent("done"))
    fun
  }
}

class FunctionDefinitionComponent(val presenter: FunctionDefinitionPresenter,
                                  var name: String,
                                  _params: FunctionParameterComponent*)
  extends VerticalLayout {

  private val stepList = new ExpressionListComponent

  // Initialize Presenter
  presenter.params_=(params.map {
    param: FunctionParameterComponent => param.presenter
  })

  // Initialize UI
  spacing = false
  add(header)
  add(body)
  add(footer)

  private def header: Component = {
    new FunctionDefinitionHeader(name, this)
  }

  private def body: Component = {
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

  private def footer: Component = {
    val footer = new CssLayout()
    footer.styleName = FunctionDefinitionComponent.StyleFoot
    footer
  }

  def params = _params.toList

  def addParam(view: FunctionParameterComponent): FunctionDefinitionComponent = {
    // TODO add parameter to view
    //    _params += view
    presenter.addParam(view.presenter)
    this
  }

  def addStep(view: ExpressionView[ExpressionPresenter]): FunctionDefinitionComponent = {
    stepList.add(view)
    presenter.addStep(view.presenter)
    this
  }

}

private class FunctionDefinitionHeader(name: String,
                                       definition: FunctionDefinitionComponent)
  extends HorizontalLayout {

  // TODO should be a label that, when clicked, becomes a text field

  styleName = FunctionDefinitionComponent.StyleHead
  spacing = true
  add(nameField)
  add(parameterLayout)
  add(addParameterButton)
  add(doCompileButton)

  private def nameField: Component = {
    val nameField = new TextField
    nameField.value = name
    nameField.styleName = FunctionDefinitionComponent.StyleHeadNameField
    nameField
  }

  private def parameterLayout: Component = {
    println("adding parameters: " + definition.params.toList)
    val paramLayout = new HorizontalLayout
    paramLayout.spacing = true
    definition.params.foreach {
      param: FunctionParameterComponent =>
        println("adding parameter: " + param)
        paramLayout.add(param)
    }
    paramLayout
  }

  private def addParameterButton: Component = Button(
  "+", {
    val header = FunctionDefinitionHeader.this
    val children = header.components.size
    header.add(new FunctionParameterComponent, children - 1)
    () // how do I avoid this?
  })

  private def doCompileButton: Component = Button(
  "Compile", {
    val compiled = definition.presenter.compile()
    println("Compiled: " + compiled)
    () // how do I avoid this?
  })

}