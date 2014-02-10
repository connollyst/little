package com.quane.little.ide.view


object ConditionalView {
  val Style = "l-if"
  val StyleIfHead = Style + "-head"
  val StyleElseHead = Style + "-else-head"
  val StyleBody = Style + "-body"
  val StyleBodyLeft = Style + "-body-left"
  val StyleFoot = Style + "-foot"
}

class ConditionalView(label: String) extends VerticalLayout {

  val thenList = new ExpressionListView
  val elseList = new ExpressionListView

  spacing = false
  styleName = ConditionalView.Style
  addComponent(thenHeader())
  addComponent(thenBody())
  addComponent(elseHeader())
  addComponent(elseBody())
  addComponent(footer())

  def thenHeader(): Component = {
    new ConditionalViewHeader("if <" + label + ">")
  }

  def thenBody(): Component = {
    val body = new HorizontalLayout
    val bodyLeft = new Label
    bodyLeft.height = new Measure(100, Units.pct)
    bodyLeft.styleName = ConditionalView.StyleBodyLeft
    body.addComponent(bodyLeft)
    body.addComponent(thenList)
    body.styleName = ConditionalView.StyleBody
    body.spacing = false
    body
  }

  def elseHeader(): Component = {
    val elseHeader = new Label
    elseHeader.value = "else"
    elseHeader.styleName = ConditionalView.StyleElseHead
    elseHeader
  }

  def elseBody(): Component = {
    val body = new HorizontalLayout
    val bodyLeft = new Label
    bodyLeft.height = new Measure(100, Units.pct)
    bodyLeft.styleName = ConditionalView.StyleBodyLeft
    body.addComponent(bodyLeft)
    body.addComponent(elseList)
    body.styleName = ConditionalView.StyleBody
    body.spacing = false
    body
  }

  def footer(): Component = {
    val footer = new Label()
    footer.styleName = ConditionalView.StyleFoot
    footer
  }

  def addThen(c: Component) = {
    thenList.add(c)
  }

  def addElse(c: Component) = {
    elseList.add(c)
  }

}

class ConditionalViewHeader(condition: String) extends HorizontalLayout {

  def this() {
    this("<condition>")
  }

  styleName = ConditionalView.StyleIfHead
  add(Label(condition))

}