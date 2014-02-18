package com.quane.little.ide.view.html

import vaadin.scala._
import com.quane.little.ide.view.ConditionalView
import com.quane.little.ide.presenter.{ExpressionPresenter, BlockPresenter}


object ConditionalComponent {
  val Style = "l-if"
  val StyleIfHead = Style + "-head"
  val StyleElseHead = Style + "-else-head"
  val StyleBody = Style + "-body"
  val StyleBodyLeft = Style + "-body-left"
  val StyleFoot = Style + "-foot"
}

class ConditionalComponent
  extends VerticalLayout
  with ConditionalView {

  private val thenBlockWrapper = new CssLayout
  private val elseBlockWrapper = new CssLayout

  spacing = false
  styleName = ConditionalComponent.Style
  addComponent(createThenHeader())
  addComponent(createThenBody())
  addComponent(createElseHeader())
  addComponent(createElseBody())
  addComponent(createFooter())

  private def createThenHeader(): Component = {
    new ConditionalHeader("if <TODO>")
  }

  private def createThenBody(): Component = {
    createBody(thenBlockWrapper)
  }

  private def createElseHeader(): Component = {
    val elseHeader = new Label
    elseHeader.value = "else"
    elseHeader.styleName = ConditionalComponent.StyleElseHead
    elseHeader
  }

  private def createElseBody(): Component = {
    createBody(elseBlockWrapper)
  }

  private def createBody(blockWrapper: Component): Component = {
    val body = new HorizontalLayout
    val bodyLeft = new Label
    bodyLeft.height = new Measure(100, Units.pct)
    bodyLeft.styleName = ConditionalComponent.StyleBodyLeft
    body.addComponent(bodyLeft)
    body.addComponent(blockWrapper)
    body.styleName = ConditionalComponent.StyleBody
    body.spacing = false
    body
  }

  private def createFooter(): Component = {
    val footer = new Label()
    footer.styleName = ConditionalComponent.StyleFoot
    footer
  }

  override def setConditionStatement(): ExpressionPresenter = {
    // TODO this expression layout belongs in the header
    new BlockPresenter(new BlockLayout)
  }

  override def setThenBlock(): BlockPresenter[BlockLayout] = {
    val view = new BlockLayout
    // TODO remove children, if any
    thenBlockWrapper.add(view)
    new BlockPresenter(view)
  }

  override def setElseBlock(): BlockPresenter[BlockLayout] = {
    val view = new BlockLayout
    // TODO remove children, if any
    elseBlockWrapper.add(view)
    new BlockPresenter(view)
  }

}

class ConditionalHeader(condition: String) extends HorizontalLayout {

  def this() {
    this("<condition>")
  }

  styleName = ConditionalComponent.StyleIfHead
  add(Label(condition))

}