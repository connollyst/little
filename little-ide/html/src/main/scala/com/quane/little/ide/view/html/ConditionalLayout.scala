package com.quane.little.ide.view.html

import com.quane.little.ide.view.ConditionalView
import com.quane.little.ide.presenter.{ExpressionPresenter, BlockPresenter}
import com.vaadin.ui._
import com.vaadin.server.Sizeable

object ConditionalLayout {
  val Style = "l-if"
  val StyleIfHead = Style + "-head"
  val StyleElseHead = Style + "-else-head"
  val StyleBody = Style + "-body"
  val StyleBodyLeft = Style + "-body-left"
  val StyleFoot = Style + "-foot"
}

/** An HTML layout view representing a conditional expression.
  *
  * @author Sean Connolly
  */
class ConditionalLayout
  extends VerticalLayout
  with ConditionalView
  with RemovableComponent {

  private val thenBlockWrapper = new CssLayout
  private val elseBlockWrapper = new CssLayout

  setSpacing(false)
  setStyleName(ConditionalLayout.Style)
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
    elseHeader.setValue("else")
    elseHeader.setStyleName(ConditionalLayout.StyleElseHead)
    elseHeader
  }

  private def createElseBody(): Component = {
    createBody(elseBlockWrapper)
  }

  private def createBody(blockWrapper: Component): Component = {
    val body = new HorizontalLayout
    val bodyLeft = new Label
    bodyLeft.setHeight(100, Sizeable.Unit.PERCENTAGE)
    bodyLeft.setStyleName(ConditionalLayout.StyleBodyLeft)
    body.addComponent(bodyLeft)
    body.addComponent(blockWrapper)
    body.setStyleName(ConditionalLayout.StyleBody)
    body.setSpacing(false)
    body
  }

  private def createFooter(): Component = {
    val footer = new Label()
    footer.setStyleName(ConditionalLayout.StyleFoot)
    footer
  }

  override def setConditionStatement(): ExpressionPresenter = {
    // TODO this expression layout belongs in the header
    new BlockPresenter(new BlockLayout)
  }

  override def setThenBlock(): BlockPresenter[BlockLayout] = {
    val view = new BlockLayout
    // TODO remove children, if any
    thenBlockWrapper.addComponent(view)
    new BlockPresenter(view)
  }

  override def setElseBlock(): BlockPresenter[BlockLayout] = {
    val view = new BlockLayout
    // TODO remove children, if any
    elseBlockWrapper.addComponent(view)
    new BlockPresenter(view)
  }

}

class ConditionalHeader(condition: String) extends HorizontalLayout {

  def this() {
    this("<condition>")
  }

  setStyleName(ConditionalLayout.StyleIfHead)
  addComponent(new Label(condition))

}