package com.quane.little.ide.view.html

import vaadin.scala._


object ConditionalComponent {
  val Style = "l-if"
  val StyleIfHead = Style + "-head"
  val StyleElseHead = Style + "-else-head"
  val StyleBody = Style + "-body"
  val StyleBodyLeft = Style + "-body-left"
  val StyleFoot = Style + "-foot"
}

class ConditionalComponent(label: String) extends VerticalLayout {

  val thenList = new BlockLayout
  val elseList = new BlockLayout

  spacing = false
  styleName = ConditionalComponent.Style
  addComponent(thenHeader())
  addComponent(thenBody())
  addComponent(elseHeader())
  addComponent(elseBody())
  addComponent(footer())

  def thenHeader(): Component = {
    new ConditionalHeader("if <" + label + ">")
  }

  def thenBody(): Component = {
    val body = new HorizontalLayout
    val bodyLeft = new Label
    bodyLeft.height = new Measure(100, Units.pct)
    bodyLeft.styleName = ConditionalComponent.StyleBodyLeft
    body.addComponent(bodyLeft)
    body.addComponent(thenList)
    body.styleName = ConditionalComponent.StyleBody
    body.spacing = false
    body
  }

  def elseHeader(): Component = {
    val elseHeader = new Label
    elseHeader.value = "else"
    elseHeader.styleName = ConditionalComponent.StyleElseHead
    elseHeader
  }

  def elseBody(): Component = {
    val body = new HorizontalLayout
    val bodyLeft = new Label
    bodyLeft.height = new Measure(100, Units.pct)
    bodyLeft.styleName = ConditionalComponent.StyleBodyLeft
    body.addComponent(bodyLeft)
    body.addComponent(elseList)
    body.styleName = ConditionalComponent.StyleBody
    body.spacing = false
    body
  }

  def footer(): Component = {
    val footer = new Label()
    footer.styleName = ConditionalComponent.StyleFoot
    footer
  }

  def addThen(c: Component) = {
    thenList.add(c)
  }

  def addElse(c: Component) = {
    elseList.add(c)
  }

}

class ConditionalHeader(condition: String) extends HorizontalLayout {

  def this() {
    this("<condition>")
  }

  styleName = ConditionalComponent.StyleIfHead
  add(Label(condition))

}