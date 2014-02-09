package com.quane.little.ide.view

import vaadin.scala.Component
import vaadin.scala.HorizontalLayout
import vaadin.scala.Label
import vaadin.scala.Measure
import vaadin.scala.Units
import vaadin.scala.VerticalLayout

object Conditional {
  val Style = "l-if"
  val StyleIfHead = Style + "-head"
  val StyleElseHead = Style + "-else-head"
  val StyleBody = Style + "-body"
  val StyleBodyLeft = Style + "-body-left"
  val StyleFoot = Style + "-foot"
}

class Conditional(label: String) extends VerticalLayout {

  val thenList = new ExpressionList
  val elseList = new ExpressionList

  spacing = false
  styleName = Conditional.Style
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
    bodyLeft.styleName = Conditional.StyleBodyLeft
    body.addComponent(bodyLeft)
    body.addComponent(thenList)
    body.styleName = Conditional.StyleBody
    body.spacing = false
    body
  }

  def elseHeader(): Component = {
    val elseHeader = new Label
    elseHeader.value = "else"
    elseHeader.styleName = Conditional.StyleElseHead
    elseHeader
  }

  def elseBody(): Component = {
    val body = new HorizontalLayout
    val bodyLeft = new Label
    bodyLeft.height = new Measure(100, Units.pct)
    bodyLeft.styleName = Conditional.StyleBodyLeft
    body.addComponent(bodyLeft)
    body.addComponent(elseList)
    body.styleName = Conditional.StyleBody
    body.spacing = false
    body
  }

  def footer(): Component = {
    val footer = new Label()
    footer.styleName = Conditional.StyleFoot
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

  styleName = Conditional.StyleIfHead
  add(Label(condition))

}